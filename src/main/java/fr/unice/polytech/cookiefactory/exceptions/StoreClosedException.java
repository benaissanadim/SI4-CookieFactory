package fr.unice.polytech.cookiefactory.exceptions;

/**
 * StoreClosedException thrown when store is closed
 * while client want to pick order
 */
public class StoreClosedException extends Exception {

    public StoreClosedException(String message){
        super(message);
    }
}
