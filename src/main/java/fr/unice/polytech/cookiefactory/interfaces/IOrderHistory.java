package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;

import java.util.List;

public interface IOrderHistory {

    public List<Order> getOrdersCancelled(Customer customer) ;

    public List<Order> lastTwoOrdersCanceled(Customer customer);

}
