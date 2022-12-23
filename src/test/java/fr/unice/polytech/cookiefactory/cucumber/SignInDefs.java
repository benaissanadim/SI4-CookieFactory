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

public class SignInDefs {
    @Autowired
    private CustomerRegistry registry;

    @Autowired
    private CustomerRepository repository;

    private String msg;
    private Customer customer;

    @Given("given users with email {string} and {string}")
    public void givenUsersWithEmailAnd(String arg0, String arg1) {
        Customer c = new Customer();
        c.setEmail(arg0);
        Customer c1 =  new Customer();
        c1.setEmail(arg1);
        repository.deleteAll();
        repository.save(c, UUID.randomUUID());
        repository.save(c1, UUID.randomUUID());
    }

    @When("User enters email {string} and password {string}")
    public void userEntersEmailAndPassword(String arg0, String arg1) {
        try {
            customer = registry.signUp(arg0,arg1);
        }catch (Exception e){
            msg = e.getMessage();
        }
    }

    @Then("the user is signed up")
    public void theUserIsSignedUp() {
        Assertions.assertNotNull(customer);
    }

    @When("user want to sign in with email {string} and password {string}")
    public void userWantToSignInWithEmailAndPassword(String arg0, String arg1) {
        try {
            registry.signIn(arg0,arg1);
        }catch (Exception e){
            msg = e.getMessage();
        }
    }

    @Then("user is signed in with email {string} and password {string}")
    public void userIsSignedIn(String arg0, String arg1) {
        Assertions.assertTrue(registry.signIn(arg0,arg1));
    }

    @When("user enters the email {string} and password {string}")
    public void userEntersTheEmailAndPassword(String arg0, String arg1) {
        try {
            customer = registry.signUp(arg0,arg1);
            Assertions.assertTrue(registry.signIn(arg0,arg1));
        }catch (Exception e){
            msg = e.getMessage();
        }
    }

    @Then("the user is registered")
    public void theUserIsRegistered() {
        Assertions.assertNotNull(customer);
    }

    @When("user enters new email {string} and password {string}")
    public void userEntersNewEmailAndPassword(String arg0, String arg1) {
        try {
            registry.signIn(arg0,arg1);

        }catch (Exception e){
            msg = e.getMessage();
        }
    }

    @Then("user is not signed in with email {string} and password {string}")
    public void userIsNotSignedIn(String arg0, String arg1) {
        Assertions.assertFalse(registry.signIn(arg0,arg1));
    }
}
