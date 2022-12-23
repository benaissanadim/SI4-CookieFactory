package fr.unice.polytech.cookiefactory.components;

import fr.unice.polytech.cookiefactory.connectors.strategy.SimpleCookieStrategyCalculator;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.customer.LoyalCustomer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.interfaces.LoyaltyProgramBenefits;
import fr.unice.polytech.cookiefactory.interfaces.OrderPriceCalculator;
import fr.unice.polytech.cookiefactory.interfaces.OrderTimePreparationCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderCalculatorServiceTest {
    Order order ;
    Cookie cookie1;
    Cookie cookie2;
    List<Item> items;
    SimpleCookieStrategyCalculator simpleCookieStrategyCalculator;
    @Autowired
    OrderPriceCalculator orderPriceCalculator ;

    @Autowired
    OrderTimePreparationCalculator orderTimePreparationCalculator ;

    @Autowired
    LoyaltyProgramBenefits loyaltyProgramBenefits ;

    @BeforeEach
    public void setUp() {
        order = new Order();
        cookie1 = new Cookie();
        cookie1.setPrice(1);
        cookie2 = new Cookie();
        cookie2.setPrice(1);
        items = new ArrayList<>();
        items.add(new Item(2, cookie1));
        items.add(new Item(4, cookie2));
        order.setItems(items);
        cookie1.setPreparationTime(7);
        cookie2.setPreparationTime(3);
    }

    @Test
    public void calculatePriceHT(){
        Assertions.assertEquals(6,orderPriceCalculator.calculatePriceHT(order));
    }

    @Test
    public void calculatePriceTTC(){
        Store store = new Store("shop","d",2.0);
        order.setStore(store);
        Assertions.assertEquals(6.12,orderPriceCalculator.calculatePriceTTC(order));
    }

    @Test
    public void calculatePriceFinalEligibilitytodiscount(){
        Store store = new Store("shop","d",2.0);
        order.setStore(store);
        LoyalCustomer customer = new LoyalCustomer();
        customer.setNbCookieOrdered(35);
        order.setCustomer(customer);
        simpleCookieStrategyCalculator = new SimpleCookieStrategyCalculator();
        double preparationTime = simpleCookieStrategyCalculator.calculatePreparationTime(cookie1);
        double preparationTime2 = simpleCookieStrategyCalculator.calculatePreparationTime(cookie2);
        System.out.println("preparationTime : "+preparationTime);
        System.out.println("preparationTime2 : "+preparationTime2);
        Assertions.assertEquals(5.508,orderPriceCalculator.calculatePriceFinal(order));
    }

    @Test
    public void calculatePriceFinalNotEligibilitytodiscount(){
        Store store = new Store("shop","d",2.0);
        order.setStore(store);
        LoyalCustomer customer = new LoyalCustomer();
        customer.setNbCookieOrdered(20);
        order.setCustomer(customer);
        Assertions.assertEquals(6.12,orderPriceCalculator.calculatePriceFinal(order));
    }

    @Test
    public void finalPriceNotLoyalCustomer(){
        Customer customer = new Customer();
        order.setCustomer(customer);
        Assertions.assertEquals(6,orderPriceCalculator.calculatePriceFinal(order));
    }

    @Test
    public void calculPreparationtimeOrder(){
        Assertions.assertEquals(26 , orderTimePreparationCalculator.calculatePreparationTime(order));
    }

    @Test
    public void calculatePrepTimeForCookForOrder1(){
        Assertions.assertEquals(45 , orderTimePreparationCalculator.calculatePrepTimeForCook(order));

    }

    @Test
    public void getBeginPrepTimeForOrder1(){
        order.setPickUpDateTime(LocalDateTime.of(2022, Month.DECEMBER,14,18,30,0,0));
        Assertions.assertEquals(LocalTime.of(17,45),orderTimePreparationCalculator.getBeginPrepTime(order));
    }
}
