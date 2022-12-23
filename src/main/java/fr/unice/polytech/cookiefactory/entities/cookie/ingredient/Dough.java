package fr.unice.polytech.cookiefactory.entities.cookie.ingredient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * a class for dough  ingredient
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Dough extends Ingredient {

    public Dough(String name, double price) {
        super(name, price, IngredientType.DOUGH);

    }
}
