package fr.unice.polytech.cookiefactory.connectors.builder;

import fr.unice.polytech.cookiefactory.entities.cookie.CookingType;
import fr.unice.polytech.cookiefactory.entities.cookie.MixType;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.CookieType;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.*;
import fr.unice.polytech.cookiefactory.exceptions.InvalidCookieRecipeException;
import fr.unice.polytech.cookiefactory.repositories.CookieRepository;
import fr.unice.polytech.cookiefactory.repositories.IngredientRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * a class Cookie builder
 */
@Getter
@Setter
@Component
public class CookieBuilder implements IBuilder {

    IngredientRepository ingredientRepository;
    CookieRepository cookieRepository ;

    protected String name;
    protected MixType mix;
    protected CookingType cooking;
    protected Dough dough;
    protected Flavour flavour;
    List<Topping> toppings;
    CookieType type;


    @Autowired
    public CookieBuilder(IngredientRepository ingredientRepository , CookieRepository cookieRepository) {
        toppings = new ArrayList<>();
        this.ingredientRepository = ingredientRepository;
        this.cookieRepository = cookieRepository ;
        this.type = CookieType.SIMPLE;
    }


    public CookieBuilder withDough(String dough) throws InvalidCookieRecipeException {
        toppings = new ArrayList<>();
        Optional<Ingredient> optIngredient = ingredientRepository.findByNameAndType(dough, IngredientType.DOUGH);
        if (optIngredient.isEmpty()) {
            throw new InvalidCookieRecipeException("parameters cannot be null");
        }
        this.dough = (Dough) optIngredient.get();
        return this;
    }


    public CookieBuilder withFlavour(String flavor) throws InvalidCookieRecipeException {
        Optional<Ingredient> optIngredient = ingredientRepository.findByNameAndType(flavor, IngredientType.FLAVOUR);
        if (optIngredient.isEmpty()) {
            throw new InvalidCookieRecipeException("no flavour");
        } else {
            this.flavour = (Flavour) optIngredient.get();
        }
        return this;
    }

    public CookieBuilder withTopping(String topping) throws InvalidCookieRecipeException {
        Optional<Ingredient> optIngredient = ingredientRepository.findByNameAndType(topping, IngredientType.TOPPING);
        if (this.toppings.size() >= 3) {
            throw new InvalidCookieRecipeException("toppings must not exceed 3");
        } else if( optIngredient.isEmpty()){
            throw new InvalidCookieRecipeException("topping not found");
        }else {
            this.toppings.add((Topping) optIngredient.get());
        }
        return this;
    }

    public CookieBuilder withCooking(String cooking) throws InvalidCookieRecipeException {
        try {
            this.cooking = CookingType.valueOf(cooking);
        } catch (IllegalArgumentException e) {
            throw new InvalidCookieRecipeException("cooking type not found");
        }
        return this;
    }

    public CookieBuilder withMix(String mix) throws InvalidCookieRecipeException {
        try {
            this.mix = MixType.valueOf(mix);
        } catch (IllegalArgumentException e) {
            throw new InvalidCookieRecipeException("mix type not found");
        }
        return this;
    }

    public CookieBuilder withName(String name){
        this.name = name;
        return this;
    }


    /**
     * Build a cookie recipe being created
     *
     * @return the cookie
     */
    public SimpleCookie build() throws InvalidCookieRecipeException {
        if (mix == null || cooking == null || dough == null || name == null) {
            throw new InvalidCookieRecipeException("invalid cookie");
        }
        SimpleCookie simpleCookie = new SimpleCookie(this);
        cookieRepository.save(simpleCookie , simpleCookie.getId());
        return new SimpleCookie(this);
    }
}
