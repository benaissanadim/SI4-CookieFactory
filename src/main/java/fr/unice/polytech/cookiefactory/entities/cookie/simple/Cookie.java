package fr.unice.polytech.cookiefactory.entities.cookie.simple;


import fr.unice.polytech.cookiefactory.entities.cookie.CookingType;
import fr.unice.polytech.cookiefactory.entities.cookie.MixType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Cookie {

    protected UUID id;
    protected boolean validated;
    protected double price;
    protected String name;
    protected long preparationTime;
    protected CookingType cooking;
    protected MixType mix;
    protected CookieType type;

}
