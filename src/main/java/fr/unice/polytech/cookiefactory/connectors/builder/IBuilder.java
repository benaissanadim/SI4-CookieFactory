package fr.unice.polytech.cookiefactory.connectors.builder;

import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.exceptions.InvalidCookieRecipeException;

public interface IBuilder {
    Cookie build() throws InvalidCookieRecipeException;


}
