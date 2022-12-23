package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.components.order.OrderCalculatorService;
import fr.unice.polytech.cookiefactory.components.order.OrderHistoryService;
import fr.unice.polytech.cookiefactory.connectors.strategy.SimpleCookieStrategyCalculator;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.customer.LoyalCustomer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Topping;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.interfaces.LoyaltyProgramBenefits;
import fr.unice.polytech.cookiefactory.repositories.CustomerRepository;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class OrderPriceStepDef {


    Order order;
    private Store store;
    private SimpleCookie cookie1;
    private SimpleCookie cookie2;
    private double priceTTC=0.0;
    private double priceHT=0.0;
    private double price=0.0;
    private Customer customer;

    private LoyalCustomer loyalCustomer;
    SimpleCookieStrategyCalculator simpleCookieStrategyCalculator;
    @Autowired
    LoyaltyProgramBenefits loyaltyProgramBenefits ;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderHistoryService orderHistoryService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    StoreRepository storeRepository;
    SimpleCookieStrategyCalculator simpleCookieStrategyCalculatorMock;
    @Autowired
    OrderCalculatorService orderCalculatorService;


    @Before
    public void init(){
        order = new Order();
    }
    @Given("cookie {string} with dough {string} price {double} topping {string} price {double} {string} price {double}")
    public void cookieWithDoughPriceToppingPricePrice(String arg0, String arg1, double arg2, String arg3, double arg4, String arg5, double arg6) {
        cookie1 = new SimpleCookie();
        cookie1.setName(arg0);
        cookie1.setDough(new Dough(arg1,arg2));
        cookie1.setToppings(Arrays.asList(new Topping(arg3, arg4), new Topping(arg5,arg6)));
        simpleCookieStrategyCalculator = new SimpleCookieStrategyCalculator();
        cookie1.setPrice(simpleCookieStrategyCalculator.calculatePrice(cookie1));

    }

    @And("cookie {string} with dough {string} price {double}, {string} topping price {double}")
    public void cookieWithDoughPriceToppingPrice(String arg0, String arg1, double arg2, String arg3, double arg4) {
        cookie2 = new SimpleCookie();
        cookie2.setName(arg0);
        cookie2.setDough(new Dough(arg1,arg2));

        cookie2.setToppings(List.of(new Topping(arg3, arg4)));
        cookie2.setPrice(simpleCookieStrategyCalculator.calculatePrice(cookie2));


    }
    @When("client make order of {int} {string} and {int} {string}")
    public void clientMakeOrderOfAnd(int arg0, String arg1, int arg2, String arg3) {
        Item item1 = new Item(arg0,cookie1);
        Item item2 = new Item(arg2,cookie2);
        double preparationTime = simpleCookieStrategyCalculator.calculatePreparationTime(cookie1);
        double preparationTime2 = simpleCookieStrategyCalculator.calculatePreparationTime(cookie2);
        System.out.println("preparationTime : "+preparationTime);
        System.out.println("preparationTime2 : "+preparationTime2);
        order.setItems(List.of(item1,item2));

    }

    @And("want to get the total price")
    public void wantToGetTheTotalPrice() {
        priceHT = orderCalculatorService.calculatePriceHT(order);

        order.setPrice(priceHT);
    }
    @Then("total price is {double}") public void totalPriceIs(double arg0) {

        Assertions.assertEquals(arg0, priceHT);

    }

    @Given("store {string} with taxe {double}%")
    public void storeWithTaxe(String arg0, double arg1) {
        store = new Store();
        store.setName(arg0);
        store.setTaxPercentage(arg1);

    }

    @And("client choose the store {string} to pick order")
    public void clientChooseTheStoreToPickOrder(String arg0) {
        storeRepository = new StoreRepository();
        storeRepository.save(store,new UUID(1,1));
        order.setStore(store);

    }

    @And("want to get the total price with taxes")
    public void wantToGetTheTotalPriceWithTaxes() {
        priceTTC = orderCalculatorService.calculatePriceTTC(order);
        System.out.println(priceTTC);
    }
    @Then("total price with taxes is {double}")
    public void totalPriceWithTaxesIs(double arg0) {
        Assertions.assertEquals(arg0, priceTTC);
    }

    @And("client having already {int} cookies ordered")
    public void clientHavingAlreadyCookiesOrdered(int arg0) {
        loyalCustomer = new LoyalCustomer();
        loyalCustomer.setNbCookieOrdered(arg0);
        order.setCustomer(loyalCustomer);

        order.setCustomer(customer);
    }

    @And("want to get the final price")
    public void wantToGetTheFinalPrice() {
        //order.setDiscount(order.canHaveDiscount());
        order.setPrice(orderCalculatorService.calculatePriceFinal(order));
        price = order.getPrice();
    }

    @Then("client have discount")
    public void clientHaveDiscount() {
        Assertions.assertTrue(loyaltyProgramBenefits.eligibleToDiscount(loyalCustomer));
    }
    @And("final price is {double}")
    public void finalPriceIs(double arg0) {

    }
}
