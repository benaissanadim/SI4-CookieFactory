package fr.unice.polytech.cookiefactory.entities.cookie.ingredient;

import lombok.*;

import java.util.UUID;

/**
 * a class represents ingredient
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "type"})
public class Ingredient {

    protected String name;
    protected double price;
    protected IngredientType type;
    protected UUID id;

    public Ingredient(String name, double price, IngredientType type) {
        this.name = name;
        this.price = price;
        this.type = type;
        id = UUID.randomUUID();
    }
}
