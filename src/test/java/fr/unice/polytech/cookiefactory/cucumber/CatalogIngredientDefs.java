package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientType;
import fr.unice.polytech.cookiefactory.interfaces.CatalogIngredientModifier;
import fr.unice.polytech.cookiefactory.repositories.IngredientRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class CatalogIngredientDefs {

    @Autowired
    private CatalogIngredientModifier mofidier;

    @Autowired
    private IngredientRepository ingredientRepository;

    private int nb;


    @When("manager ask for number of ingredients in catalog")
    public void managerAskForNumberOfIngredientsInCatalog() {
        ingredientRepository.deleteAll();
        nb = mofidier.getNumberIngredients();
    }

    @Then("{int} found")
    public void found(int arg0) {
        Assertions.assertEquals(arg0, nb);
    }

    @When("manager add a new ingredient {string} price {double} and name {string}")
    public void managerAddANewIngredientPriceAndName(String arg0, double arg1, String arg2) {
        ingredientRepository.deleteAll();
        mofidier.addIngredientToCatalog(IngredientType.valueOf(arg0), arg2, arg1);
    }

    @Then("ingredients added successfully")
    public void ingredientsAddedSuccessfully() {
    }

    @When("manager ask again for number of ingredients in catalog") public void managerAskAgainForNumberOfIngredientsInCatalog() {
        nb = mofidier.getNumberIngredients();
    }
}
