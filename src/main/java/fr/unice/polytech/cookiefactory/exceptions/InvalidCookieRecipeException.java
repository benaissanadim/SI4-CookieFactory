package fr.unice.polytech.cookiefactory.exceptions;

/**
 * InvalidCookieRecipeException thrown when building a cookie is invalid to build
 */
public class InvalidCookieRecipeException extends Exception {
    public InvalidCookieRecipeException(String message) {
        super(message);
    }
}
