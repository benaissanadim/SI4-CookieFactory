package fr.unice.polytech.cookiefactory.exceptions;

/**
 * InvalidCookieRecipeException thrown when building a cookie is invalid to build
 */
public class TimerTaskException extends Exception {
    public TimerTaskException(String message) {
        super(message);
    }
}
