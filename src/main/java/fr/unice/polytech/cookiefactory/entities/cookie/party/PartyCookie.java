package fr.unice.polytech.cookiefactory.entities.cookie.party;

import fr.unice.polytech.cookiefactory.connectors.builder.PartyCookieBuilder;
import fr.unice.polytech.cookiefactory.entities.cookie.Theme;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientDose;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PartyCookie extends Cookie {

    private List<IngredientDose> ingredients;
    private CookieSize size;
    private Theme theme;

    public PartyCookie(PartyCookieBuilder cookieBuilder){
        this.name = cookieBuilder.getName();
        this.cooking = cookieBuilder.getCooking();
        this.mix = cookieBuilder.getMix();
        this.ingredients = cookieBuilder.getIngredients();
        this.size = cookieBuilder.getSize();
        this.type = cookieBuilder.getType();
        this.id = UUID.randomUUID();
    }
}
