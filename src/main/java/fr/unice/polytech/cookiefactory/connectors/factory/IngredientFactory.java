package fr.unice.polytech.cookiefactory.connectors.factory;

import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.*;

public class IngredientFactory {

    private IngredientFactory(){}

    public static Ingredient create(String name, double price, IngredientType type) {
        return switch (type) {
            case DOUGH -> new Dough(name, price);
            case FLAVOUR -> new Flavour(name, price);
            case TOPPING -> new Topping(name, price);
        };
    }
}
