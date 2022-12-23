package fr.unice.polytech.cookiefactory.connectors.strategy;

import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.cookie.party.PartyCookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientDose;

import java.util.List;

public class PartyCookieStrategyCalculator implements CookieStrategyCalculator {
    @Override
    public double calculatePrice(Cookie cookie) {

        PartyCookie personnalizedCookie = (PartyCookie) cookie;
        List<IngredientDose> ingredientDoses = personnalizedCookie.getIngredients();

        double price = 0;
        for(IngredientDose ingredientDose : ingredientDoses){
            price+= ingredientDose.getIngredient().getPrice()*ingredientDose.getDose();
        }

        switch (personnalizedCookie.getSize()){
            case L -> price *=4;
            case XL -> price *=5;
            case XXL -> price *=6;
        }

        return price;
    }

    @Override
    public long calculatePreparationTime(Cookie cookie) {
        return (long) (cookie.getPreparationTime()*1.25);
    }
}
