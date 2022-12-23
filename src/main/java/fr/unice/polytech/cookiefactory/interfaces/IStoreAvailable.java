package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.store.Store;

import java.time.LocalDateTime;

public interface IStoreAvailable {

    /**
     * verify if a store is available at a specific time
     *
     * @param dateTime dat and time
     * @return true if store is not closed and still available
     */
    public boolean isAvailable(Store store , LocalDateTime dateTime) ;


}
