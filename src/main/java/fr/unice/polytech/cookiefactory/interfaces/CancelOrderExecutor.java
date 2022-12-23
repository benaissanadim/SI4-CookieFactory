package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.InabilityToOrderException;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;
import fr.unice.polytech.cookiefactory.exceptions.StockException;

public interface CancelOrderExecutor {
    void cancelOrder(Customer customer, Order order)
            throws InabilityToOrderException, StockException, InvalidPaymentException;

}
