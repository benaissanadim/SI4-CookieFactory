package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.customer.LoyalCustomer;
import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.EmailValidationException;
import fr.unice.polytech.cookiefactory.exceptions.JoinLoyaltyProgramException;
import fr.unice.polytech.cookiefactory.exceptions.PasswordValidationException;
import fr.unice.polytech.cookiefactory.exceptions.SignUpException;
import fr.unice.polytech.cookiefactory.interfaces.CustomerRegistry;
import fr.unice.polytech.cookiefactory.repositories.CustomerRepository;
import fr.unice.polytech.cookiefactory.interfaces.CustomerEligibility;
import fr.unice.polytech.cookiefactory.interfaces.LoyaltyProgramBenefits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Math.abs;
import static java.time.temporal.ChronoUnit.MINUTES;

@Component
public class CustomerManager implements LoyaltyProgramBenefits, CustomerRegistry, CustomerEligibility {

    private static final String REGEX_PATTERN_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private static final String REGEX_PATTERN_PWD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"
                                                    + "(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

    CustomerRepository customerRepository;
    OrderHistoryService orderHistoryService;
    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);

    @Autowired
    public CustomerManager(CustomerRepository customerRepository, OrderHistoryService orderHistoryService){
        this.customerRepository = customerRepository ;
        this.orderHistoryService =orderHistoryService ;
    }

    public boolean eligibleToDiscount(Customer customer) {
        if(! (customer instanceof LoyalCustomer loyalCustomer)){
            return false;
        }
        if(loyalCustomer.getNbCookieOrdered() >= 30){
            loyalCustomer.setNbCookieOrdered(0);
            customerRepository.save(customer,customer.getId());
            return true;
        }
        return false;
    }

    public boolean eligibleToOrder(Customer customer, LocalDateTime date){
        List<Order> orders = orderHistoryService.lastTwoOrdersCanceled(customer);
        if(orders.isEmpty()){
            return true;
        }
        Order last = orders.get(0);
        Order beforeLast = orders.get(1);
        logger.info("last order canceled : " + last.getOrderState().getStatus());
        logger.info(last.getStatusDateChange().toString());
        logger.info(beforeLast.getStatusDateChange().toString());
        return abs(MINUTES.between(last.getStatusDateChange(),
                beforeLast.getStatusDateChange())) >8 ||
         ChronoUnit.MINUTES.between(last.getStatusDateChange(), date) > 10;
    }

    public Customer signUp(String email, String pwd)
            throws EmailValidationException, PasswordValidationException, SignUpException {
        Pattern validEmailPattern = Pattern.compile(REGEX_PATTERN_EMAIL, Pattern.CASE_INSENSITIVE);
        if(!validEmailPattern.matcher(email).find()){
            throw new EmailValidationException("invalid email!");
        }
        Pattern validPwdPattern = Pattern.compile(REGEX_PATTERN_PWD);
        if(!validPwdPattern.matcher(pwd).matches()){
            throw new PasswordValidationException("invalid password!");
        }
        if(customerRepository.findByEmail(email)){
            throw new SignUpException("email already used!");
        }

        Customer user = new Customer();
        user.setPassword(pwd);
        user.setEmail(email);
        customerRepository.save(user, user.getId());
        return user;
    }

    public Boolean signIn(String email, String pwd){
        return customerRepository.findByEmailPwd(email, pwd);
    }

    public void joinLoyaltyProgram(Customer customer) throws JoinLoyaltyProgramException {
        if(customer instanceof LoyalCustomer) {
            throw new JoinLoyaltyProgramException("you have already joined loyalty program");
        }
        LoyalCustomer loyalCustomer = new LoyalCustomer(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPassword(), customer.getCreditCard(), customer.getCart());
        customerRepository.deleteById(customer.getId());

        customerRepository.save(loyalCustomer, loyalCustomer.getId());
    }


}