package fr.unice.polytech.cookiefactory.repositories;

import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderSurpriseCart;
import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class CookRepositoryTest {
    @Autowired
    CookRepository cookRepository ;
    Cook cook1 ;
    Cook cook2 ;
    Store store ;

    @BeforeEach
    public void setUp(){
        cook1 = new Cook();
        store = new Store("Paul","antibes",0.6);
        cook2 = new Cook();
        store.getCooks().add(cook1);
        store.getCooks().add(cook2);
        cookRepository.save(cook1 , new UUID(1,1));
        cookRepository.save(cook2 , new UUID(2,2));
    }

    @Test
    public void getCooksByStore(){
        List<Cook> cooks = cookRepository.getCooksByStore(store);
        Assertions.assertTrue(cooks.containsAll(List.of(cook1,cook2)));
    }

    @Test
    public void getCookiesFromOrders(){
        ReminderSurpriseCart reminderSurpriseCart = new ReminderSurpriseCart(store,new OrderRepository(),new SurpriseCartRepository());
        List<Order> orderList = new ArrayList<>();
        reminderSurpriseCart.getCookiesFromOrders(orderList);
        Assertions.assertEquals(0, reminderSurpriseCart.getCookiesFromOrders(orderList).size());
    }

}
