package fr.unice.polytech.cookiefactory.exceptions;

/**
 * InvalidPaymentException thrown when payment is invalid
 */
public class InvalidPaymentException extends Exception {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
