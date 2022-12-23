package fr.unice.polytech.cookiefactory.connectors.strategy;

import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Flavour;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Topping;

import java.util.List;

public class SimpleCookieStrategyCalculator implements CookieStrategyCalculator{

    @Override public double calculatePrice(Cookie cookie) {
        SimpleCookie simpleCookie = (SimpleCookie) cookie;
        double price = 0;
        Dough dough = simpleCookie.getDough();
        Flavour flavour = simpleCookie.getFlavour();
        List<Topping> toppings = simpleCookie.getToppings();
        if(dough!=null) price += dough.getPrice();
        if(flavour!=null) price += flavour.getPrice();
        if(toppings!=null) price += toppings.stream().mapToDouble(Topping::getPrice).sum();
        return price;
    }

    @Override public long calculatePreparationTime(Cookie cookie) {
        return cookie.getPreparationTime();
    }
}
