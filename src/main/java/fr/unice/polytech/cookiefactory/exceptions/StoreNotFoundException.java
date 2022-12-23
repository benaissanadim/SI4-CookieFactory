package fr.unice.polytech.cookiefactory.exceptions;

/**
 * StoreNotFoundException thrown when a store is not found
 */
public class StoreNotFoundException extends Exception {

    public StoreNotFoundException(String message){
        super(message);
    }

}
