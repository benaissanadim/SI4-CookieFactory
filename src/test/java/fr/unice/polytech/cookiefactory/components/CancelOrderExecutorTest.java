package fr.unice.polytech.cookiefactory.components;

import fr.unice.polytech.cookiefactory.components.cookie.CatalogCookieService;
import fr.unice.polytech.cookiefactory.components.order.CancelOrderManager;
import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.entities.customer.CreditCard;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.CookieType;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Flavour;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.entities.store.stocks.Stock;
import fr.unice.polytech.cookiefactory.entities.store.stocks.StockIngredient;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class CancelOrderExecutorTest {
    private Customer customer ;
    private Store store ;
    private Dough dough ;
    private Flavour flavour ;
    private Item item1 ;

    private SimpleCookie cookie1 ;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired CancelOrderManager cancelOrderManager;

    @Autowired CatalogCookieService catalogCookieService;


    @BeforeEach
    public  void  setUp(){
        StockIngredient stockIngredient = new StockIngredient();
        dough = new Dough("dough", 10);
        flavour = new Flavour("flavour", 10);
        stockIngredient.put(dough, 10);
        stockIngredient.put(flavour, 10);
        Stock stock = new Stock();
        store = new Store();
        CreditCard creditCard = new CreditCard();
        creditCard.setExpirationDate(LocalDate.now().plusYears(5));
        customer = new Customer("Eric",creditCard);
        cookie1 = new SimpleCookie();
        cookie1.setType(CookieType.SIMPLE);
        cookie1.setName("Chocolala");
        item1 = new Item(2 , cookie1 );
        catalogCookieService.suggestCookieInCatalog(cookie1);
        SimpleCookie cookie2 = new SimpleCookie();
        cookie2.setName("Vanille");
        ingredientRepository.save(dough, new UUID(1,1) );
        Item item2 = new Item(2, cookie2);
        catalogCookieService.suggestCookieInCatalog(cookie2);
        //set available ingredients to "dough"
        stock.setReservedStock(stockIngredient);
        stock.setAvailableStock(stockIngredient);
        store.setStock(stock);

        customer.setId(new UUID(0,0));
    }

    @Test
    public void testCancelOrder() throws StockException, InvalidPaymentException {
        //create order
        Order order = new Order();
        OrderState orderState = new OrderState();
        orderState.setStatus(OrderStatus.PLACED);
        order.setCustomer(customer);
        order.setStore(store);
        order.setOrderState(orderState);
        order.setPrice(10);
        cookie1.setDough(dough);
        cookie1.setFlavour(flavour);
        order.setItems(List.of(item1));
        order.setId(UUID.randomUUID());
        orderRepository.save(order, order.getId());

        cancelOrderManager.cancelOrder(customer, order);
        assertEquals(OrderStatus.CANCELED, order.getOrderState().getStatus());

    }

}
