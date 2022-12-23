package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;

import java.util.List;

public interface CatalogExplorator {

    List<Cookie> listPreMadeRecipes();

    List<Cookie> exploreCatalog(String regexp);

}