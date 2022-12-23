package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.customer.LoyalCustomer;
import fr.unice.polytech.cookiefactory.interfaces.CustomerEligibility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class EligibilityToDiscountDefs {


    Customer customer;
    LoyalCustomer loyalCustomer;
    boolean test;

    @Autowired
    private CustomerEligibility eligibility;


    @When("a client without a loyalty program wants to get discount")
    public void aClientWithoutALoyaltyProgramWantsToGetDiscount() {
        customer = new Customer();
        test = eligibility.eligibleToDiscount(customer);
    }

    @Then("discount not applied")
    public void discountNotApplied() {
        Assertions.assertFalse(test);
    }

    @Given("a loyal client with {int} cookies ordered")
    public void aLoyalClientWithCookiesOrdered(int arg0) {
        loyalCustomer = new LoyalCustomer();
        loyalCustomer.setNbCookieOrdered(arg0);
    }

    @When("client want discount") public void clientWantDiscount() {
        test = eligibility.eligibleToDiscount(customer);
    }

    @Then("discount applied")
    public void discountApplied() {
        Assertions.assertTrue(test);

    }

    @And("number cookies became {int} again")
    public void numberCookiesBecameAgain(int arg0) {
        Assertions.assertEquals(arg0, loyalCustomer.getNbCookieOrdered());
    }

    @When("loyal client want discount")
    public void loyalClientWantDiscount() {
        test = eligibility.eligibleToDiscount(loyalCustomer);
    }
}
