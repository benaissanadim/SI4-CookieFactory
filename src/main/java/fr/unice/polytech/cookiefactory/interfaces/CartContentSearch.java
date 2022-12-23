package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.order.Item;

import java.util.List;
import java.util.Optional;

public interface CartContentSearch {
    Optional<Item> getItemCookie(Customer customer, Cookie cookie);
    List<Item> getContents(Customer customer);
}
