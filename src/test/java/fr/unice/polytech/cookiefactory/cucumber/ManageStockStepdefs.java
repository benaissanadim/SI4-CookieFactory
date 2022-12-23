package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.components.store.StockService;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Topping;
import fr.unice.polytech.cookiefactory.entities.store.stocks.Stock;
import fr.unice.polytech.cookiefactory.entities.store.stocks.StockIngredient;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.repositories.IngredientRepository;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManageStockStepdefs {

    private Dough dough2;
    private Dough dough1;
    private Topping topping;
    private Store store;
    private int nbIngredient;
    private StockIngredient stockIngredient ;
    private int nbChocolate;
    private int nbVanille;
    private Stock stock;

    @Autowired
    StockService stockService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    IngredientRepository catalog;
    @Given("a factory with a store")
    public void aFactoryWithAStore() {
        store = new Store();
        storeRepository.save(store,new UUID(0,0));

    }

    @And("catalog ingredient")
    public void catalogIngredient() {
        catalog = new IngredientRepository();
    }

    @And("an ingredient called {string}")
    public void anIngredientCalled(String arg0) {
        dough1 = new Dough(arg0, 5);
        catalog.save(dough1,new UUID(0,0));
    }

    @And("an other ingredient called {string}")
    public void anOtherIngredientCalled(String arg0) {
        topping = new Topping(arg0, 4);
        stock = new Stock();
        catalog.save(topping,new UUID(1,1));
        stockIngredient = new StockIngredient();

        store.setStock(stock);
        stock.setAvailableStock(stockIngredient);
    }

    @When("the manager asks for the number of ingredients in stock")
    public void theManagerAsksForTheNumberOfIngredientsInStock() {
        nbIngredient = store.getStock().getAvailableStock().size();
    }

    @Then("There are {int} Ingredient in the stock")
    public void thereAreIngredientInTheStock(int arg0) {
        assertEquals(arg0, nbIngredient);
    }

    @When("the manager adds {int} {string}")
    public void theManagerAdds(int arg0, String arg1) throws StockException {
        stockIngredient.put(dough1, arg0);
        stock.setAvailableStock(stockIngredient);
    }

    @And("the manager asks for the number of ingredients")
    public void theManagerAsksForTheNumberOfIngredients() {
        nbIngredient = store.getStock().getAvailableStock().size();
    }

    @Then("There are {int} Ingredient in stock")
    public void thereAreIngredientInStock(int arg0) {
        assertEquals(arg0, nbIngredient);
    }

    @When("the manager asks for the number of {string}")
    public void theManagerAsksForTheNumberOf(String arg0) {
        nbChocolate = store.getStock().getAvailableStock().get(dough1);

    }

    @Then("There are {int} in his number of {string}")
    public void thereAreInHisNumberOf(int arg0, String arg1) {
        assertEquals(arg0, nbChocolate);
    }

    @When("the manager adds more {int} {string}")
    public void theManagerAddsMore(int arg0, String arg1) throws StockException {

        stockService.increase(stockIngredient, dough1, arg0);
        stock.setAvailableStock(stockIngredient);
    }

    @And("the manager asks again for the number of {string}")
    public void theManagerAsksAgainForTheNumberOf(String arg0) {
        nbChocolate = store.getStock().getAvailableStock().get(dough1);
    }

    @Then("There are now {int} in his number of {string}")
    public void thereAreNowInHisNumberOf(int arg0, String arg1) {
        assertEquals(arg0, nbChocolate);
    }

    @Given("stock is now with {int} {string}")
    public void stockIsNowWith(int arg0, String arg1) throws StockException {
        stockService = new StockService();
        stockService.increase(stockIngredient, dough1, arg0);

    }

    @When("the manager adds another {int} {string}")
    public void theManagerAddsAnother(int arg0, String arg1) throws StockException {
        stockIngredient.put(topping, arg0);

        stock.setAvailableStock(stockIngredient);
    }

    @And("the manager asks now  for the number of ingredients")
    public void theManagerAsksNowForTheNumberOfIngredients() {
        nbIngredient = store.getStock().getAvailableStock().size();
    }

    @Then("There are now {int} Ingredient in stock")
    public void thereAreNowIngredientInStock(int arg0) {
        assertEquals(arg0, nbIngredient);
    }

    @When("the manager asks for the number of new ingredient {string}")
    public void theManagerAsksForTheNumberOfNewIngredient(String arg0) {
        nbVanille = store.getStock().getAvailableStock().get(topping);
    }

    @Then("finally {int} in his number of {string}")
    public void finallyInHisNumberOf(int arg0, String arg1) {
        assertEquals(arg0, nbVanille);
    }

    @When("the manager adds ingredients {int} {string}")
    public void theManagerAddsIngredients(int arg0, String arg1) {


    }

    @Then("ingredients can't be added to stock")
    public void ingredientsCanTBeAddedToStock() {
    }

    @And("the manager takes {int} {string}")
    public void theManagerTakes(int arg0, String arg1) {
    }

    @Then("stock has {int} {string}")
    public void stockHas(int arg0, String arg1) {
    }

    @Then("the manager cant take {int} {string}")
    public void theManagerCantTake(int arg0, String arg1) {
    }

    @And("cant take {int} {string}")
    public void cantTake(int arg0, String arg1) {
    }
}
