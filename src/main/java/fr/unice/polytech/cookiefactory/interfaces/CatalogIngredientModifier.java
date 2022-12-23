package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientType;

public interface CatalogIngredientModifier {

    void addIngredientToCatalog(IngredientType type, String name, double price);

    int getNumberIngredients();

    }
