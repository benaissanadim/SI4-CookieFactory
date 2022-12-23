package fr.unice.polytech.cookiefactory.entities.cookie.ingredient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * a class for flavour ingredient
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Flavour extends Ingredient {
    public Flavour(String name, double price) {
        super(name, price, IngredientType.FLAVOUR);

    }
}
