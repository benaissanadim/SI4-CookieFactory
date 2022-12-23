package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.exceptions.InvalidCookieRecipeException;

public interface CatalogUpdater {

    public void suggestCookieInCatalog(Cookie cookie);
    public void validateCookiesInCatalog(double priceThreshold);
}
