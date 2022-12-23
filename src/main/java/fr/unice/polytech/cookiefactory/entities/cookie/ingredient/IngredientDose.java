package fr.unice.polytech.cookiefactory.entities.cookie.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IngredientDose {

    private int dose;
    private Ingredient ingredient;

}
