package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.connectors.builder.CookieBuilder;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Flavour;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientType;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Topping;
import fr.unice.polytech.cookiefactory.exceptions.InvalidCookieRecipeException;
import fr.unice.polytech.cookiefactory.repositories.CookieRepository;
import fr.unice.polytech.cookiefactory.repositories.IngredientRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildCookieDefs {
    private SimpleCookie cookie;
    long contains ;
    String mssg ;

    @Autowired
    CookieBuilder builder, builder1, builder2, builder3, builder4, builder5 ;


    @Autowired
    IngredientRepository ingredientRepository ;

    @Autowired
    CookieRepository cookieRepository ;

    @Given("factory with empty catalog cookie")
    public void factoryWithEmptyCatalogCookie() {

    }

    @And("catalog ingredient with dough {string} flavour {string} topping {string} {string} {string} {string}")
    public void catalogIngredientWithDoughFlavourTopping(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        Dough dough = new Dough(arg0 , 1);
        dough.setType(IngredientType.DOUGH);
        Flavour flavour = new Flavour(arg1,1);
        flavour.setType(IngredientType.FLAVOUR);
        Topping topping1 = new Topping(arg2,1);
        topping1.setType(IngredientType.TOPPING);
        Topping topping2 = new Topping(arg3,1);
        topping2.setType(IngredientType.TOPPING);
        Topping topping3 = new Topping(arg4,1);
        topping3.setType(IngredientType.TOPPING);

        ingredientRepository.save(flavour, UUID.randomUUID());
        ingredientRepository.save(dough,UUID.randomUUID());
        ingredientRepository.save(topping1,new UUID(7,7));
        ingredientRepository.save(topping2,UUID.randomUUID());
        ingredientRepository.save(topping3,UUID.randomUUID());

    }

    @When("the factory wants cookie recipe number")
    public void theFactoryWantsCookieRecipeNumber() {
        cookieRepository.deleteAll();
        contains = cookieRepository.count();
        System.out.println(contains);
    }

    @Then("There is {int} cookie")
    public void thereIsCookie(int arg0) {
        assertEquals(arg0 , ( int) contains);
    }

    @When("the factory create cookie {string} with dough {string} flavour {string} topping {string} {string} {string} {string} mix and {string} cooking")
    public void theFactoryCreateCookieWithDoughFlavourToppingMixAndCooking(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) throws InvalidCookieRecipeException {
        cookieRepository.deleteAll();
        cookie =  builder2.withDough(arg1).withCooking(arg7).withMix(arg6).withFlavour(arg2)
                .withTopping(arg3).withTopping(arg4).withName(arg0).build();
    }


    @Then("catalog contain {int} cookie")
    public void catalogContainCookie(int arg0) {
        assertEquals(arg0, (int) cookieRepository.count());
    }


    @When("the factory create a new cookie recipe {string} with dough {string}, {string} topping, {string} mix and {string} cooking")
    public void theFactoryCreateANewCookieRecipeWithDoughToppingMixAndCooking(String arg0, String arg1, String arg2, String arg3, String arg4) throws InvalidCookieRecipeException {
        cookieRepository.deleteAll();
        cookie = builder3.withDough(arg1).withCooking(arg4).withMix(arg3)
                .withTopping(arg2).withName(arg0)
                .build();
    }

    @When("the factory create a new recipe named {string} with {string} dough, {string} {string} {string} {string} toppings, {string} mix and {string} cooking")
    public void theFactoryCreateANewRecipeNamedWithDoughToppingsMixAndCooking(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7) throws InvalidCookieRecipeException {
        try{
            builder4.setToppings(new ArrayList<>());
            cookie =  builder4.withDough(arg1).withCooking(arg7).withMix(arg6)
                    .withTopping(arg2).withTopping(arg3).withTopping(arg4)
                    .withName(arg0).withTopping(arg5).build();
        }catch (InvalidCookieRecipeException e){
             mssg = e.getMessage();
        }
    }

    @Then("recipe is not created {string}")
    public void recipeIsNotCreated(String arg0) {
        Assertions.assertEquals(arg0,mssg);
    }

    @When("the factory create a new recipe named {string} with {string} mix")
    public void theFactoryCreateANewRecipeNamedWithMix(String arg0, String arg1) {
        try{
            builder5.setToppings(new ArrayList<>());
            cookie =  builder5.withName(arg0).withMix(arg1).build();
        }catch (InvalidCookieRecipeException e){
            mssg = e.getMessage();
        }
    }

    @When("the factory create a new recipe named {string} with {string} cooking type")
    public void theFactoryCreateANewRecipeNamedWithCookingType(String arg0, String arg1) {
        try{
            builder5.setToppings(new ArrayList<>());
            cookie =  builder5.withCooking(arg1).withName(arg0).build();
        }catch (InvalidCookieRecipeException e){
            mssg = e.getMessage();
        }
    }

    @When("the factory create a new recipe empty")
    public void theFactoryCreateANewRecipeEmpty() {
        try{
            builder5.setToppings(new ArrayList<>());
            cookie =  builder5.withName(null).build();
        }catch (InvalidCookieRecipeException e){
            mssg = e.getMessage();
        }
    }
}
