package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.SpecialOrder;
import fr.unice.polytech.cookiefactory.entities.store.SpecialStore;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.StoreClosedException;
import fr.unice.polytech.cookiefactory.exceptions.StoreNotFoundException;

import java.time.LocalDateTime;

public interface IChooseStoreTime {

    void chooseStore(Order order, Store store) throws StoreNotFoundException;
    void choosePickUpTime(Order order, LocalDateTime dateTime) throws StoreNotFoundException,
            StoreClosedException;
}
