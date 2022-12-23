package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.StockException;

public interface IStockCancelor {

    public void cancelReservation(Store store, Order order) throws StockException;
}
