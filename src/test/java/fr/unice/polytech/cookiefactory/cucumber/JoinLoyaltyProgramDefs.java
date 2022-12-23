package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.customer.LoyalCustomer;
import fr.unice.polytech.cookiefactory.exceptions.JoinLoyaltyProgramException;
import fr.unice.polytech.cookiefactory.interfaces.LoyaltyProgramBenefits;
import fr.unice.polytech.cookiefactory.repositories.CustomerRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class JoinLoyaltyProgramDefs {
    Customer customer ;
    LoyalCustomer loyalCustomer  ;
    String msg ;

    @Autowired
    LoyaltyProgramBenefits loyaltyProgramBenefits ;

    @Autowired
    CustomerRepository customerRepository ;

    @Given("I am client {string}")
    public void iAmClient(String arg0) {
        customer = new Customer();
        customer.setName(arg0);
        customer.setId(UUID.randomUUID());
        System.out.println("okk");
        System.out.println(customer.getId());
        customerRepository.save(customer, customer.getId());
    }

    @When("I want to join the loyalty program")
    public void iWantToJoinTheLoyaltyProgram() throws JoinLoyaltyProgramException {
        loyaltyProgramBenefits.joinLoyaltyProgram(customer);
    }

    @Then("I am member of the loyalty program")
    public void iAmMemberOfTheLoyaltyProgram() {
        System.out.println(customerRepository.findAll().size());
        System.out.println(customer.getId());
        System.out.println(customerRepository.findAll().get(0).getId());
        Optional<Customer> loyalCustomer = customerRepository.findById(customer.getId());
        Assertions.assertTrue(loyalCustomer.isPresent());
        Assertions.assertTrue(loyalCustomer.get() instanceof LoyalCustomer);
    }

    @Given("I am client {string} having loyalty program")
    public void iAmClientHavingLoyaltyProgram(String arg0) {
        customerRepository.deleteAll();
        loyalCustomer = new LoyalCustomer();
        loyalCustomer.setName(arg0);
        customerRepository.save(loyalCustomer, loyalCustomer.getId());
    }

    @When("Iwant to  join the loyalty program")
    public void iwantToJoinTheLoyaltyProgram() throws JoinLoyaltyProgramException {
        try {
            loyaltyProgramBenefits.joinLoyaltyProgram(loyalCustomer);
        }catch (JoinLoyaltyProgramException e){
            msg = e.getMessage();
        }

    }

    @Then("exception with {string}")
    public void exceptionWith(String arg0) {
        Assertions.assertEquals(arg0, msg);

    }
}
