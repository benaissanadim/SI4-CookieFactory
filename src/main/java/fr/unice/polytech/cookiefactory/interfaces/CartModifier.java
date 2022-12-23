package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.EmptyCartException;

public interface CartModifier {

    void addItemToCart(Customer customer, Cookie cookie, int quantity);
    void removeFromCart(Customer customer , Item item);
    Order validate(Customer customer) throws EmptyCartException;

}
