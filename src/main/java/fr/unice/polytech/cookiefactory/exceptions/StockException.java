package fr.unice.polytech.cookiefactory.exceptions;

/**
 * StockException thrown when stock is no more available
 */
public class StockException extends Exception {

    public StockException(String message) {
        super(message);
    }
}
