package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.StockException;

public interface IStockPreparation {

    public void prepare(Store store , Order order) throws StockException;



}
