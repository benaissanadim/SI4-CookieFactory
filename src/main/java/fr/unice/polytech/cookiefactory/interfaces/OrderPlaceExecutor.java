package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.InabilityToOrderException;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;
import fr.unice.polytech.cookiefactory.exceptions.NoCookAvailableException;
import fr.unice.polytech.cookiefactory.exceptions.StockException;

public interface OrderPlaceExecutor {

    void validateOrder(Customer customer, Order order)
            throws NoCookAvailableException, InabilityToOrderException, StockException, InvalidPaymentException,
            InvalidPaymentException;
}
