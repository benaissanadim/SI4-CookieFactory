package fr.unice.polytech.cookiefactory.exceptions;
/**
 * InabilityToOrderException thrown when a customer is unable to order
 */
public class InabilityToOrderException extends Exception {
    public InabilityToOrderException(String message) {
        super(message);
    }
}

