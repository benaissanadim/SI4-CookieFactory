package fr.unice.polytech.cookiefactory.entities.order;


import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * a class that represents an order
 */
@Getter
@Setter
public class Order {
    private UUID id;
    private double price;
    private List<Item> items;
    private Customer customer;
    private Store store;
    private OrderState orderState;
    private LocalDateTime PickUpDateTime;
    private LocalDateTime statusDateChange;
    private Cook cook;

    public Order(){
        orderState = new OrderState();
    }
}
