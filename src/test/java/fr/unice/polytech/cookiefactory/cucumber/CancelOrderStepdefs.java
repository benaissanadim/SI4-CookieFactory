package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.components.order.CancelOrderManager;
import fr.unice.polytech.cookiefactory.components.cookie.CatalogCookieService;
import fr.unice.polytech.cookiefactory.components.store.StockManagerService;
import fr.unice.polytech.cookiefactory.components.store.StockService;
import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.entities.customer.CreditCard;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.CookieType;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Flavour;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Ingredient;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.entities.store.stocks.Stock;
import fr.unice.polytech.cookiefactory.entities.store.stocks.StockIngredient;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.interfaces.IStockCancelor;
import fr.unice.polytech.cookiefactory.interfaces.StockModifier;
import fr.unice.polytech.cookiefactory.repositories.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CancelOrderStepdefs {
    private Customer customer ;
    private Store store ;
    private StockIngredient reserved ;
    private StockIngredient available ;
    private Ingredient ingredient ;
    private Dough dough ;
    private Flavour flavour ;
    private OrderState orderState ;
    private Item item1 ;
    private Item item2 ;


    private SimpleCookie cookie1 ;
    private SimpleCookie cookie2 ;

    private Order order ;
    private Stock stock ;


    @Autowired
    IStockCancelor stockCanceler;
    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StockService stockService;

    @Autowired
    StockModifier stockModifier;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    StockManagerService stockManagerService;

    @Autowired
    CancelOrderManager cancelOrderManager;

    @Autowired
    CatalogCookieService catalogCookieService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CookieRepository cookieRepository;
    @Given("a store {string}")
    public void aStore(String arg0) {
        store = new Store();
        store.setName(arg0);
    }

    @And("reserved stock of {int} {string} and {int} {string}")
    public void reservedStockOfAnd(int arg0, String arg1, int arg2, String arg3) {
        stock = new Stock();
        ingredientRepository = new IngredientRepository();
        reserved = new StockIngredient();
        dough = new Dough(arg1,2);
        flavour = new Flavour(arg3,2);
        ingredientRepository.save(dough, new UUID(1,1));
        ingredientRepository.save(flavour, new UUID(1,1));
        reserved.put(dough, arg0);
        reserved.put(flavour, arg2);
        stock.setReservedStock(reserved);
    }

    @And("available stock of {int} {string} {int} {string}")
    public void availableStockOf(int arg0, String arg1, int arg2, String arg3) {
        orderState = new OrderState();
        orderState.setStatus(OrderStatus.PLACED);
        order = new Order();
        order.setOrderState(orderState);
        ingredientRepository.save(dough, new UUID(1,1));
        ingredientRepository.save(flavour, new UUID(1,1));
        available  = new StockIngredient();
        available.put(dough,arg0);
        available.put(flavour,arg2);
        stock.setAvailableStock(available);
    }

    @And("factory with tickets order id {string} and {string}")
    public void factoryWithTicketsOrderIdAnd(String arg0, String arg1) {
    }
    @Given("client with credit card amount {int}")
    public void client_with_credit_card_amount(Integer int1) {
        customer = new Customer();
        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationDate(LocalDate.now().plusYears(5));
        customer.setCreditCard(creditCard);
        customer.getCreditCard().setAmount(int1);
        customer.setName("eric");
        customerRepository.save(customer, UUID.randomUUID());
    }


    @Given("order {string} with ticket id {string} and price {int}")
    public void anOrder(String arg0, String arg1, int arg2) {
        orderState = new OrderState();
        orderState.setStatus(OrderStatus.valueOf(arg0));
        order.setOrderState(orderState);
        order.setPrice(arg2);
        order.setCustomer(customer);

    }
    @And("having required stock of {int} {string} and {int} {string}")
    public void havingRequiredStockOfAnd(int arg0, String arg1, int arg2, String arg3) {
        SimpleCookie cookie1 = new SimpleCookie();
        cookie1.setDough(dough);
        cookie1.setType(CookieType.SIMPLE);
        cookie1.setFlavour(flavour);
        item1 = new Item(arg0 , cookie1 );
        order.setItems(List.of(item1));
        store.setStock(stock);
        order.setStore(store);
        order.setId(UUID.randomUUID());
        orderRepository.save(order, order.getId());
    }

    @When("client cancel order")
    public void clientCancelOrder() throws StockException, InvalidPaymentException {
        cancelOrderManager.cancelOrder(customer,order);
    }

    @Then("order is Cancelled")
    public void orderIsCancelled() {
        System.out.println(stock.getReservedStock());
        assertEquals(OrderStatus.CANCELED, order.getOrderState().getStatus());
    }

    @And("reserved stock is {int} {string} and {int} {string}")
    public void reservedStockIsAnd(int arg0, String arg1, int arg2, String arg3) {
        assertEquals((int)stock.getReservedStock().get(dough), arg0);
        assertEquals((int)stock.getReservedStock().get(flavour), arg2);
    }

    @And("available stock is {int} {string} {int} {string}")
    public void availableStockIs(int arg0, String arg1, int arg2, String arg3) {
        assertEquals((int)stock.getAvailableStock().get(dough), arg0);
        assertEquals((int)stock.getAvailableStock().get(flavour), arg2);
    }

    @And("factory remove ticket id {string}")
    public void factoryRemoveTicketId(String arg0) {
    }
    @And("client credit card became {double}")
    public void clientCreditCardBecame(double int1) {
        assertEquals(int1,customer.getCreditCard().getAmount());
    }

    @Then("order is still {string}")
    public void orderIsStill(String arg0) {
        assertEquals(OrderStatus.valueOf(arg0),order.getOrderState().getStatus());
    }
}
