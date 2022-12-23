package fr.unice.polytech.cookiefactory.components;

import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.customer.LoyalCustomer;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.exceptions.EmailValidationException;
import fr.unice.polytech.cookiefactory.exceptions.PasswordValidationException;
import fr.unice.polytech.cookiefactory.exceptions.SignUpException;
import fr.unice.polytech.cookiefactory.interfaces.CustomerEligibility;
import fr.unice.polytech.cookiefactory.interfaces.CustomerRegistry;
import fr.unice.polytech.cookiefactory.interfaces.LoyaltyProgramBenefits;
import fr.unice.polytech.cookiefactory.repositories.CustomerRepository;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@SpringBootTest
public class CustomerManagerTest {
    Customer customer ;
    LoyalCustomer loyalcustomer ;

    @Autowired
    LoyaltyProgramBenefits loyaltyProgramBenefits ;

    @Autowired
    CustomerRegistry customerRegistry ;

    @Autowired
    CustomerEligibility customerEligibility ;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;



    @BeforeEach
    public void setUp(){
        customer = new Customer();
        loyalcustomer = new LoyalCustomer();
        loyalcustomer.setNbCookieOrdered(29);
    }

    @Test
    public void eligibleToDiscountNotLoyal(){
        Assertions.assertFalse(loyaltyProgramBenefits.eligibleToDiscount(customer));
    }

    @Test
    public void eligibleToDiscountLoyal(){
        loyalcustomer.setNbCookieOrdered(35);
        boolean s = loyalcustomer instanceof LoyalCustomer ;
        System.out.println(s);
        boolean t = loyalcustomer.getNbCookieOrdered()>30 ;
        System.out.println(t);
        Assertions.assertTrue(loyaltyProgramBenefits.eligibleToDiscount(loyalcustomer));
    }

    @Test
    public void  eligibleToDiscountLessThen30(){
        Assertions.assertFalse(loyaltyProgramBenefits.eligibleToDiscount(customer));
    }

    @Test
    public void  eligibleToDiscountMoreThen30(){
        loyalcustomer.setNbCookieOrdered(31);
        Assertions.assertFalse(loyaltyProgramBenefits.eligibleToDiscount(customer));
    }

    @Test
    public void signUpSuccess() throws PasswordValidationException, EmailValidationException, SignUpException {
        String email = "hz1@gmail.com" ;
        String pwd = "iI36Iql4fr#&8L";
        Customer user = customerRegistry.signUp(email , pwd);
        Assertions.assertNotNull(user);
        UUID uuid = user.getId();
        Assertions.assertTrue(customerRepository.existsById(uuid));
    }

    @Test
    public void signUpFailedWeakPWd() throws PasswordValidationException, EmailValidationException, SignUpException {
        String email = "hz2@gmail.com" ;
        String pwd = "1234";
        Assertions.assertThrows(PasswordValidationException.class, () ->customerRegistry.signUp(email,pwd));
    }

    @Test
    public void sigInSuccess() throws PasswordValidationException, EmailValidationException, SignUpException {
        String email = "hz2@gmail.com" ;
        String pwd = "iI36Iql4fr#&8L";
        customer.setEmail(email);
        customer.setPassword(pwd);
        customerRepository.save(customer , customer.getId());
        Assertions.assertTrue(customerRegistry.signIn(email,pwd));
    }

    @Test
    public void sigInFailed() throws PasswordValidationException, EmailValidationException, SignUpException {
        customerRepository.deleteAll();
        String email = "hz2@gmail.com" ;
        String pwd = "iI36Iql4fr#&8L";
        Assertions.assertFalse(customerRegistry.signIn(email,pwd));
    }

    @Test
    public void eligibleToOrderNoOrderHistory(){
        Assertions.assertTrue(customerEligibility.eligibleToOrder(customer,LocalDateTime.now()));
    }

    @Test
    public void eligibleToOrder(){
        Order order1 = new Order();
        order1.setStatusDateChange(LocalDateTime.of(2022, Month.DECEMBER,14,6,30,0,0));
        Order order2 = new Order();
        order2.setStatusDateChange(LocalDateTime.of(2022, Month.DECEMBER,14,6,40,0,0));
        order1.setOrderState(new OrderState());
        order1.getOrderState().setStatus(OrderStatus.CANCELED);
        order2.setOrderState(new OrderState());
        order2.getOrderState().setStatus(OrderStatus.CANCELED);
        orderRepository.save(order1,UUID.randomUUID());
        orderRepository.save(order2,UUID.randomUUID());
        Assertions.assertTrue(customerEligibility.eligibleToOrder(customer,(LocalDateTime.of(2022, Month.DECEMBER,14,7,30,0,0))));
    }

    @Test
    public void notEligibleTwoCancelledin8min(){
        orderRepository.deleteAll();
        Order order1 = new Order();
        order1.setStatusDateChange(LocalDateTime.of(2022, Month.DECEMBER,14,6,30,0,0));
        Order order2 = new Order();
        order2.setStatusDateChange(LocalDateTime.of(2022, Month.DECEMBER,14,6,32,0,0));
        order1.setOrderState(new OrderState());
        order1.getOrderState().setStatus(OrderStatus.CANCELED);
        order2.setOrderState(new OrderState());
        order2.getOrderState().setStatus(OrderStatus.CANCELED);
        orderRepository.save(order1,UUID.randomUUID());
        orderRepository.save(order2,UUID.randomUUID());
        Assertions.assertFalse(customerEligibility.eligibleToOrder(customer,(LocalDateTime.of(2022, Month.DECEMBER,14,6,35,0,0))));

    }

    @Test
    public void  notEligibleTenMinutesNotPassed(){
        orderRepository.deleteAll();
        Order order1 = new Order();
        order1.setStatusDateChange(LocalDateTime.of(2022, Month.DECEMBER,14,6,30,0,0));
        Order order2 = new Order();
        order2.setStatusDateChange(LocalDateTime.of(2022, Month.DECEMBER,14,6,32,0,0));
        order1.setOrderState(new OrderState());
        order1.getOrderState().setStatus(OrderStatus.CANCELED);
        order2.setOrderState(new OrderState());
        order2.getOrderState().setStatus(OrderStatus.CANCELED);
        orderRepository.save(order1,new UUID(0,0));
        orderRepository.save(order2,new UUID(1,1));
        Assertions.assertFalse(customerEligibility.eligibleToOrder(customer,(LocalDateTime.of(2022, Month.DECEMBER,14,6,35,0,0))));
    }
}
