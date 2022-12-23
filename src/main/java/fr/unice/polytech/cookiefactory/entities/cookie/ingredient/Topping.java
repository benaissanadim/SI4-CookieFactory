package fr.unice.polytech.cookiefactory.entities.cookie.ingredient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * a class for Topping  ingredient
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Topping extends Ingredient {

    public Topping(String name, double price ) {
        super(name, price, IngredientType.TOPPING);

    }
}
