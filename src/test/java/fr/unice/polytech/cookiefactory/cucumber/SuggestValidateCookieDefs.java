package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.components.cookie.CatalogCookieService;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.exceptions.InvalidCookieRecipeException;
import fr.unice.polytech.cookiefactory.repositories.CookieRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SuggestValidateCookieDefs {

    @Autowired private CookieRepository cookieRepository;
    @Autowired private CatalogCookieService catalog;

    Cookie chocoCookie;
    Cookie vanillaCookie;

    @Given("An empty catalog")
    public void emptyCatalog() {
        cookieRepository.deleteAll();
    }

    @And("cookies suggested {string} price of {double}")
    public void iHaveCookiesInMySuggestedCatalogCookiesPriceOf(String arg1, double arg2) throws
            InvalidCookieRecipeException {
        chocoCookie = new Cookie();
        chocoCookie.setName(arg1);
        chocoCookie.setPrice(arg2);
        chocoCookie.setValidated(false);
        chocoCookie.setId(UUID.randomUUID());
        cookieRepository.save(chocoCookie, chocoCookie.getId());
    }

    @And("{string} price of {double}")
    public void priceOf(String arg0, double arg1){
        vanillaCookie = new Cookie();
        vanillaCookie.setName(arg0);
        vanillaCookie.setPrice(arg1);
        vanillaCookie.setValidated(false);
        vanillaCookie.setId(UUID.randomUUID());
        cookieRepository.save(vanillaCookie, vanillaCookie.getId());
    }

    @When("the manager want to validate them in the cookie catalog according to a threshold of {double}")
    public void iWantToAddThemInTheCookieCatalogAccordingToAThresholdOf(double arg0) {
        catalog.validateCookiesInCatalog(arg0);
    }

    @Then("the manager have the {string} cookie added to the catalog")
    public void iHaveTheCookieAddedToTheCatalog(String arg0) {
        assertTrue(cookieRepository.findAll().contains(chocoCookie));
        assertTrue(chocoCookie.isValidated());
    }

    @And("the {string} cookie removed")
    public void iHaveTheCookieRemovedToTheSuugestedCatalog(String arg0) {
        assertFalse(cookieRepository.findAll().contains(vanillaCookie));

    }

    @When("manager suggest a cookie {string}")
    public void managerSuggestACookie(String arg0) throws InvalidCookieRecipeException {
        cookieRepository.deleteAll();
        chocoCookie= new Cookie();
        chocoCookie.setName(arg0);
        catalog.suggestCookieInCatalog(chocoCookie);
    }

    @Then("cookie is added to catalog with validation false")
    public void cookieIsAddedToCatalogWithValidationFalse() {
        assertFalse(chocoCookie.isValidated());
    }

}
