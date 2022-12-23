package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.CookieType;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Topping;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.entities.store.stocks.Stock;
import fr.unice.polytech.cookiefactory.entities.store.stocks.StockIngredient;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.*;
import fr.unice.polytech.cookiefactory.interfaces.IStartCookingOrder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class StartCookingOrderStepDefs {

    private Order order;
    private Store store;
    private Stock stock;
    private StockIngredient reserved;
    private StockIngredient available;
    private Cook cook ;

    @Autowired
    IStartCookingOrder startCookingOrder ;


    @Given("store {string}") public void store(String arg0) {
        store = new Store();
        store.setName(arg0);
    }

    @And("reserved stock {int} {string} {int} {string} and {int} {string}")
    public void reservedStockAnd(int arg0, String arg1, int arg2, String arg3, int arg4, String arg5) {
        stock = new Stock();
        reserved = new StockIngredient();
        available = new StockIngredient();

        Dough ingredient1 = new Dough(arg1,2);
        Topping ingredient2 = new Topping(arg3,2);
        Topping ingredient3 = new Topping(arg5,2);
        Dough dough = new Dough(arg1,2);
        Topping flavour = new Topping(arg3,2);
        Topping topping = new Topping(arg5,2);
        available.put(dough,arg0);
        available.put(flavour,arg2);
        available.put(topping,arg4);
        reserved.put(ingredient1,arg0);
        reserved.put(ingredient2,arg2);
        reserved.put(ingredient3,arg4);
        stock.setReservedStock(reserved);
        stock.setAvailableStock(available);
        store.setStock(stock);
    }

    @And("{string} order with {int} cookies of {int} {string} and {int} {string}")
    public void orderWithCookiesOfAnd(String arg0, int arg1, int arg2, String arg3, int arg4, String arg5) {
        order = new Order();
        order.setOrderState(new OrderState());
        order.getOrderState().next();
        Dough ingredient1 = new Dough(arg3,2);
        Topping ingredient2 = new Topping(arg5,2);
        Item orderItem = new Item();
        SimpleCookie cookie =new SimpleCookie();
        cookie.setDough(ingredient1);
        cookie.setType(CookieType.SIMPLE);
        cookie.setToppings(List.of(ingredient2));
        orderItem.setCookie(cookie);
        orderItem.setQuantity(arg1);
        order.setItems(List.of(orderItem));
        order.setStore(store);
    }

    @And("a cook affected to the order working in that store")
    public void aCookAffectedToTheOrderWorkingInThatStore() {
        cook = new Cook();
        order.setCook(cook);
    }

    @When("the cook validate the start of cooking of the order")
    public void theCookValidateTheStartOfCookingOfTheOrder() throws StockException, TimerTaskException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        order.setStore(store);
        store.setStock(stock);
        startCookingOrder.startCookingOrder(order);
    }

    @Then("the status of the order {string}")
    public void theStatusOfTheOrder(String arg0) {
        System.out.println(order.getOrderState().getState()+"state");
        assertEquals(OrderStatus.valueOf(arg0), order.getOrderState().getStatus());
    }

    @Given("reserved stock is {int} {string} {int} {string} and {int} {string}")
    public void reservedStockIsAnd(int arg0, String arg1, int arg2, String arg3, int arg4, String arg5) {
        Dough ingredient1 = new Dough(arg1,2);
        Topping ingredient2 = new Topping(arg3,2);
        Topping ingredient3 = new Topping(arg5,2);

        assertEquals(arg0, stock.getAvailableStock().get(ingredient1));
        assertEquals(arg2, stock.getAvailableStock().get(ingredient2));
        assertEquals(arg4, stock.getAvailableStock().get(ingredient3));

    }

    @Given("another reserved stock {int} {string} {int} {string} and {int} {string}")
    public void anotherReservedStockAnd(int arg0, String arg1, int arg2, String arg3, int arg4, String arg5) {
        stock = new Stock();
        reserved = new StockIngredient();
        Dough ingredient1 = new Dough(arg1,2);
        Topping ingredient2 = new Topping(arg3,2);
        Topping ingredient3 = new Topping(arg5,2);
        reserved.put(ingredient1,arg0);
        reserved.put(ingredient2,arg2);
        reserved.put(ingredient3,arg4);
        stock.setReservedStock(reserved);
        System.out.println(reserved);
        store.setStock(stock);
    }

    @When("the cook validate this start of cooking of the order")
    public void theCookValidateThisStartOfCookingOfTheOrder() {
        order.setStore(store);
        store.setStock(stock);
        order.setOrderState(new OrderState());
        order.getOrderState().next();
        assertThrows(StockException.class , () ->startCookingOrder.startCookingOrder(order));
    }

    @Then("the status of the order still {string}")
    public void theStatusOfTheOrderStill(String arg0) {
        assertEquals(OrderStatus.valueOf(arg0), order.getOrderState().getStatus());
    }

    @And("reserved stock still {int} {string} {int} {string} and {int} {string}")
    public void reservedStockStillAnd(int arg0, String arg1, int arg2, String arg3, int arg4, String arg5) {
        assertEquals(reserved, stock.getReservedStock());
    }

    @And("cook") public void cook() {
        cook = new Cook();
    }


}
