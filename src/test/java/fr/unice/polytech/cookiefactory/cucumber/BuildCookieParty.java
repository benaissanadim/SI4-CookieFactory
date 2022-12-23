package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.connectors.builder.CookieBuilder;
import fr.unice.polytech.cookiefactory.connectors.builder.PartyCookieBuilder;
import fr.unice.polytech.cookiefactory.connectors.strategy.CookieStrategyCalculator;
import fr.unice.polytech.cookiefactory.connectors.strategy.PartyCookieStrategyCalculator;
import fr.unice.polytech.cookiefactory.entities.cookie.CookingType;
import fr.unice.polytech.cookiefactory.entities.cookie.MixType;
import fr.unice.polytech.cookiefactory.entities.cookie.Theme;
import fr.unice.polytech.cookiefactory.entities.cookie.party.CookieSize;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.CookieType;
import fr.unice.polytech.cookiefactory.entities.cookie.party.PartyCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.*;
import fr.unice.polytech.cookiefactory.repositories.CookieRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuildCookieParty {

    @Autowired CookieBuilder builder;

    @Autowired PartyCookieBuilder partyCookieBuilder;

    private SimpleCookie cookie;
    private PartyCookie partyCookie, partyCookie2;
    private Theme t;
    private Ingredient ingredient;

    @Autowired CookieRepository cookieRepository;

    @Given("a cookie {string} with dough {string} flavour {string} topping {string} {string} {string} {string} mix and {string} cooking")
    public void anCookieWithDoughFlavourToppingMixAndCooking(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) {
        cookie = new SimpleCookie();
        cookie.setId(UUID.randomUUID());
        cookie.setName(arg0);
        cookie.setDough(new Dough(arg1, 1));
        cookie.setFlavour(new Flavour(arg2, 1));
        cookie.setType(CookieType.SIMPLE);
        cookie.setMix(MixType.valueOf(arg6));
        cookie.setCooking(CookingType.valueOf(arg7));
        List<Topping> toppings = new ArrayList<>();
        toppings.add(new Topping(arg3,1));
        toppings.add(new Topping(arg4,1));
        toppings.add(new Topping(arg5,1));
        cookie.setToppings(toppings);
    }

    @And("with {int}$ price") public void with$Price(int arg0) {
        cookie.setPrice(arg0);
    }

    @When("customer makes size {string} and {string}")
    public void customerMakesSize(String arg0, String arg1) {
        t = Theme.valueOf(arg1);
        partyCookie = partyCookieBuilder.withCookie(cookie).withSize(CookieSize.valueOf(arg0))
                .withTheme(t).build();
        cookieRepository.save(partyCookie, partyCookie.getId());

    }

    @Then("a cookie party is created")
    public void aCookiePartyIsCreated() {
        Assertions.assertTrue(cookieRepository.findById(partyCookie.getId())
                .isPresent());
    }

    @And("price {int}$ for the party cookie")
    public void price$ForThePartyCookie(int arg0) {
        CookieStrategyCalculator calcul = new PartyCookieStrategyCalculator();
        double x =calcul.calculatePrice(partyCookie);
        Assertions.assertEquals(arg0, x);
    }

    @When("customer add ingredient {string} of price {double} with dose {int}"
            + " and size {string}")
    public void customerAddIngredientIngredientOfPricePWithDoseX(
            String arg0, double arg1, int arg2, String s) {
        ingredient = new Ingredient(arg0,arg1, IngredientType.DOUGH);
        partyCookieBuilder = partyCookieBuilder.withCookie(cookie)
                .addIngredient(ingredient, arg2).withSize(CookieSize.valueOf(s));
        partyCookie = partyCookieBuilder.build();
        cookieRepository.save(partyCookie, partyCookie.getId());
    }

    @Then("theme new is {string} now")
    public void themeNewIsThemeN(String arg0) {
        Assertions.assertEquals(arg0, t.name());
    }

    @When("remove the ingredient {string}")
    public void removeTheIngredientIngredientRemove(String arg0) {
        partyCookie = partyCookieBuilder.removeIngredient(ingredient).build();
    }

}
