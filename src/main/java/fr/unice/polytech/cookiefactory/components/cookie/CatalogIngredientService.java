package fr.unice.polytech.cookiefactory.components.cookie;

import fr.unice.polytech.cookiefactory.connectors.factory.IngredientFactory;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Ingredient;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientType;
import fr.unice.polytech.cookiefactory.interfaces.CatalogIngredientModifier;
import fr.unice.polytech.cookiefactory.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogIngredientService implements CatalogIngredientModifier {

    IngredientRepository repository;

    @Autowired
    public CatalogIngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addIngredientToCatalog(IngredientType type, String name, double price){
        Ingredient ingredient = IngredientFactory.create(name,price,type);
        repository.save(ingredient, ingredient.getId());
    }

    @Override
    public int getNumberIngredients() {
        return repository.findAll().size();
    }

}
