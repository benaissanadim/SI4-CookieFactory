package fr.unice.polytech.cookiefactory.connectors.strategy;

import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;

public interface CookieStrategyCalculator {

    double calculatePrice(Cookie cookie);
    long calculatePreparationTime(Cookie cookie);

}
