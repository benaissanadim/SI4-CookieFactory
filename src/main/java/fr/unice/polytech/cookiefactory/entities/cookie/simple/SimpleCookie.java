package fr.unice.polytech.cookiefactory.entities.cookie.simple;

import fr.unice.polytech.cookiefactory.connectors.builder.CookieBuilder;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Dough;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Flavour;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Topping;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SimpleCookie extends Cookie {

    protected Dough dough;
    protected Flavour flavour;
    protected List<Topping> toppings;

    public SimpleCookie(CookieBuilder builder){
        this.id = UUID.randomUUID();
        this.name = builder.getName();
        this.dough = builder.getDough();
        this.flavour = builder.getFlavour();
        this.toppings = builder.getToppings();
        this.mix = builder.getMix();
        this.cooking = builder.getCooking();
    }
}
