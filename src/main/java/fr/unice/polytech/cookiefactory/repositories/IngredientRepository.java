package fr.unice.polytech.cookiefactory.repositories;

import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Ingredient;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientType;
import fr.unice.polytech.repositories.BasicRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class IngredientRepository extends BasicRepositoryImpl<Ingredient, UUID> {

    public Optional<Ingredient> findByNameAndType(String name, IngredientType type) {
        return findAll().stream().filter((Ingredient ingredient) ->
                ingredient.getName().equals(name) && ingredient.getType().equals(type)).findAny();
    }
}
