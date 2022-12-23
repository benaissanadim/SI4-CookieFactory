package fr.unice.polytech.cookiefactory.connectors.builder;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import fr.unice.polytech.cookiefactory.entities.cookie.CookingType;
import fr.unice.polytech.cookiefactory.entities.cookie.MixType;
import fr.unice.polytech.cookiefactory.entities.cookie.Theme;
import fr.unice.polytech.cookiefactory.entities.cookie.party.CookieSize;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.CookieType;
import fr.unice.polytech.cookiefactory.entities.cookie.party.PartyCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.SimpleCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Ingredient;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientDose;
import fr.unice.polytech.cookiefactory.exceptions.InvalidCookieRecipeException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class PartyCookieBuilder implements IBuilder{

    private CookieSize size ;
    private SimpleCookie simpleCookie ;
    private List<IngredientDose> ingredients;
    private String name;
    private MixType mix;
    private CookingType cooking;
    private CookieType type;
    private Theme theme;
    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);



    public PartyCookieBuilder(CookieSize size) throws InvalidCookieRecipeException {
        super();
        this.size =size;
        this.type = CookieType.PARTY;

    }

    public PartyCookieBuilder withCookie(SimpleCookie cookie){
        this.simpleCookie = cookie ;
        this.ingredients = new ArrayList<>();
        ingredients.add(new IngredientDose(1, cookie.getDough()));
        if(cookie.getFlavour() != null){
            ingredients.add(new IngredientDose(1, cookie.getFlavour()));
        }
        for(Ingredient  ingredient: cookie.getToppings()){
            ingredients.add(new IngredientDose(1, ingredient));
        }
        this.name = cookie.getName()+ " personnalized";
        this.mix = cookie.getMix();
        this.cooking = cookie.getCooking();
        return this ;
    }

    public PartyCookieBuilder withTheme(Theme theme){
        if(theme==null){
            logger.warn("invalid theme");
        } else{
            this.theme = theme ;
        }
        return this ;
    }


    public PartyCookieBuilder withSize(CookieSize size){
        if(size==null){
            logger.warn("invalid size");
        } else{
            this.size = size ;
        }
        return this ;
    }

    public PartyCookieBuilder removeIngredient(Ingredient ingredient){
        IngredientDose ingredientRemove =null ;
        for(IngredientDose  ingredientDose: ingredients){
            if(ingredientDose.getIngredient().equals(ingredient)){
                ingredientRemove = ingredientDose;
                break;
            }
        }
        if(ingredientRemove != null){
            ingredients.remove(ingredientRemove);
        }

        return this ;
    }

    public PartyCookieBuilder addIngredient(Ingredient ingredient, int dose){
        ingredients.add(new IngredientDose(dose, ingredient));
        return this ;
    }

    /**
     * Build a cookie recipe being created
     *
     * @return the cookie
     */
    public PartyCookie build() {
        return new PartyCookie(this);
    }

}
