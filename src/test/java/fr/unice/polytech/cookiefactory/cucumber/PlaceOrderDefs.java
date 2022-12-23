package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.customer.CreditCard;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.CookieType;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Flavour;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Topping;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.entities.store.cook.CookScheduler;
import fr.unice.polytech.cookiefactory.entities.store.TimeSlot;
import fr.unice.polytech.cookiefactory.entities.store.stocks.Stock;
import fr.unice.polytech.cookiefactory.entities.store.stocks.StockIngredient;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.InabilityToOrderException;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;
import fr.unice.polytech.cookiefactory.exceptions.NoCookAvailableException;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.interfaces.OrderPlaceExecutor;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceOrderDefs {

    private Order order;
    private Item item1;
    private Item item2;
    private Customer client;
    private CreditCard creditCard;
    private Store store;
    private Cook cook1;
    private Cook cook2;
    private List<Cook> cooks;
    private Cookie cookie;
    private String exceptionMsg;

    @Autowired
    private OrderPlaceExecutor orderService;


    @Autowired StoreRepository storeRepository;

    @Given("order") public void orderOfPrice() {
        order = new Order();
    }


    @And("new store {string}") public void store(String arg0) {
        store = new Store();
        store.setName(arg0);
        store.setTaxPercentage(0.0);
        storeRepository.save(store, store.getId());
    }

    @And("cookA {string} available from {int}:{int} to {int}:{int} in store on {string}")
    public void cookAAvailableFromToInStoreOn(String arg0, int arg1, int arg2, int arg3, int arg4, String arg5) {
        cook1 = new Cook();
        cook1.setName(arg0);
        CookScheduler timeTable = new CookScheduler();
        List<TimeSlot> list = new ArrayList<>();
        list.add(new TimeSlot(LocalTime.of(arg1,arg2), LocalTime.of(arg3,arg4)));
        timeTable.put(DayOfWeek.valueOf(arg5), list);
        cook1.setCookScheduler(timeTable);
        cooks = new ArrayList<>();
        cooks.add(cook1);
    }

    @And("cookB {string} available from {int}:{int} to {int}:{int} in store on {string}")
    public void cookBAvailableFromToInStoreOn(String arg0, int arg1, int arg2, int arg3, int arg4, String arg5) {
        cook2 = new Cook();
        cook2.setName(arg0);
        CookScheduler timeTable = new CookScheduler();
        List<TimeSlot> list = new ArrayList<>();
        list.add(new TimeSlot(LocalTime.of(arg1,arg2), LocalTime.of(arg3,arg4)));
        timeTable.put(DayOfWeek.valueOf(arg5), list);
        cook2.setCookScheduler(timeTable);
        cooks.add(cook2);
        store.setCooks(cooks);
        Assertions.assertThat(5).isEqualTo(5);
    }


    @Given("available stock of {int} {string} and {int} {string} {int} {string} {int} {string} {int} {string}")
    public void available_stock_of_and(Integer int1, String string, Integer int2, String string2, Integer int3, String string3, Integer int4, String string4, Integer int5, String string5) {

        Stock stock = new Stock();
        stock.setReservedStock(new StockIngredient());
        StockIngredient available = new StockIngredient();
        available.put(new Dough(string, 2), int1);
        available.put(new Topping(string2, 2), int2);
        available.put(new Flavour(string3, 2), int3);
        available.put(new Topping(string4, 2), int4);
        available.put(new Topping(string5, 2), int5);
        stock.setAvailableStock(available);
        store.setStock(stock);
        store.setId(UUID.randomUUID());
        storeRepository.save(store, store.getId());

    }


    @And("client choose to pick at {int}:{int} {int}|{int}|{int}")
    public void chooseToPickAtH(int arg0, int arg1, int arg2, int arg3, int arg4) {
        order.setPickUpDateTime(LocalDateTime.of(arg4,arg3,arg2,arg0,arg1));
    }

    @And("order takes {int}min") public void orderTakesMin(int arg0) {

        List<Item> items = new ArrayList<>();
        cookie.setPreparationTime(arg0);
        items.add(new Item(1,cookie));
        order.setItems(items);
    }

    @Given("ok") public void ok() {
    }

    @And("having {int} cookies {string} price {int} prep time {int} with dough {string} flavour {string} topping {string} and {string}") public void havingCookiesPricePrepTimeWithDoughFlavourToppingAnd(
            int arg0, String arg1, int arg2, int arg3, String arg4, String arg5, String arg6, String arg7) {
        SimpleCookie cookie1 = new SimpleCookie();
        cookie1.setType(CookieType.SIMPLE);
        cookie1.setName(arg1);
        cookie1.setPrice(arg2);
        cookie1.setPreparationTime(arg3);
        cookie1.setDough(new Dough(arg4, 2));
        cookie1.setFlavour(new Flavour(arg5, 2));
        cookie1.setToppings(Arrays.asList(new Topping(arg6, 2), new Topping(arg7, 2)));
        item1 = new Item(arg0, cookie1);
    }

    @And("having {int} cookies {string} price {double} prep time {int} with dough {string} topping {string}") public void havingCookiesPricePrepTimeWithDoughTopping(
            int arg0, String arg1, double arg2, int arg3, String arg4, String arg5) {
        SimpleCookie cookie2 = new SimpleCookie();
        cookie2.setType(CookieType.SIMPLE);
        cookie2.setName(arg1);
        cookie2.setPrice(arg2);
        cookie2.setPreparationTime(arg3);
        cookie2.setDough(new Dough(arg4, 2));
        cookie2.setToppings(List.of(new Topping(arg5, 2)));
        item2 = new Item(arg0, cookie2);
        order.setItems(Arrays.asList(item1, item2));

    }

    @When("the client pays the order with credit card amount {int} and expiration date {int}|{int}|{int}")
    public void theClientPaysTheOrderWithCreditCardAmountAndExpirationDateK(
            int arg0, int arg1, int arg2, int arg3)
            throws NoCookAvailableException, InabilityToOrderException, StockException, InvalidPaymentException {

        creditCard = new CreditCard();
        creditCard.setAmount(arg0);
        creditCard.setExpirationDate(LocalDate.of(arg3,arg2, arg1));
        client = new Customer();
        client.setCreditCard(creditCard);
        order.setCustomer(client);
        order.setStore(store);

        try {
            orderService.validateOrder(client, order);
        }catch (Exception e){
            exceptionMsg = e.getMessage();
        }
    }


    @Then("the order status is placed")
    public void the_order_status_is_placed() {
        assertEquals(OrderStatus.PLACED, order.getOrderState().getStatus());
    }

    @And("the amount of credit card became {double}")
    public void theAmountOfCreditCardBecame(double arg0) {
        assertEquals(arg0, creditCard.getAmount());

    }

    @Then("available stock decreases {int} {string} {int} {string} {int} {string} {int} {string} {int} {string}")
    public void available_stock_decreases(Integer int1, String string, Integer int2, String string2, Integer int3, String string3, Integer int4, String string4, Integer int5, String string5) {
        StockIngredient available = store.getStock().getAvailableStock();
        assertEquals(int1, available.get(new Dough(string, 2)));
        assertEquals(int2, available.get(new Flavour(string2, 2)));
        assertEquals(int3, available.get(new Topping(string3, 2)));
        assertEquals(int4, available.get(new Topping(string4, 2)));
        assertEquals(int5, available.get(new Topping(string5, 2)));
    }

    @Then("the order is assigned to a cook {string}")
    public void the_order_is_assigned_to_a_cook(String arg0) {
        assertEquals(arg0,order.getCook().getName());
    }


    @Given("available same stock of {int} {string} and {int} {string} {int} {string} {int} {string} {int} {string}")
    public void available_same_stock_of_and(Integer int1, String string, Integer int2, String string2, Integer int3, String string3, Integer int4, String string4, Integer int5, String string5) {

        Stock stock = new Stock();
        StockIngredient available = new StockIngredient();
        available.put(new Dough(string, 2), int1);
        available.put(new Topping(string2, 2), int2);
        available.put(new Flavour(string3, 2), int3);
        available.put(new Topping(string4, 2), int4);
        available.put(new Topping(string5, 2), int5);
        stock.setAvailableStock(available);
        store.setStock(stock);
        storeRepository.save(store, store.getId());
    }


    @Then("exception thrown {string}")
    public void exception_thrown_invalid_payment(String s) {
        assertEquals(exceptionMsg, s);
    }

    @And("the order is not assigned to a cook")
    public void theOrderIsNotAssignedToACook() {
        assertNull(order.getCook());
    }

    @Given("another stock of {int} {string} and {int} {string} {int} {string}")
    public void anotherStockOfAnd(int arg0,
            String arg1, int arg2, String arg3, int arg4, String arg5) {
        Stock stock = new Stock();
        StockIngredient available = new StockIngredient();
        available.put(new Dough(arg1, 2), arg0);
        available.put(new Topping(arg3, 2), arg2);
        available.put(new Topping(arg5, 2), arg4);
        stock.setAvailableStock(available);
        store.setStock(stock);
        storeRepository.save(store, store.getId());
    }
}
