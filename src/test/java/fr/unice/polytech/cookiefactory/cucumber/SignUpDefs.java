package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.interfaces.CustomerRegistry;
import fr.unice.polytech.cookiefactory.repositories.CustomerRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.mockito.Mockito.mock;

public class SignUpDefs {

    @Autowired
    private CustomerRegistry registry;

    @Autowired
    private CustomerRepository repository;

    private String msg;
    private Customer customer;

    @Given("given users with emails {string} and {string}")
    public void givenUsersWithEmailsAnd(String arg0, String arg1) {
        Customer c = new Customer();
        c.setEmail(arg0);
        Customer c1 =  new Customer();
        c1.setEmail(arg1);
        repository.deleteAll();
        repository.save(c, UUID.randomUUID());
        repository.save(c1, UUID.randomUUID());
    }

    @When("user enters email {string} and password {string}")
    public void userEntersEmailEmailAndPasswordPassword(String arg0, String arg1) {
       try {
           customer = registry.signUp(arg0,arg1);
       }catch (Exception e){
           msg = e.getMessage();
       }
    }

    @Then("exception is {string}")
    public void exceptionMsg(String arg0) {
        Assertions.assertEquals(arg0, msg);
    }

    @Then("a new user is registered")
    public void aNewUserIsRegistered() {
        Assertions.assertNotNull(customer);
    }
}
