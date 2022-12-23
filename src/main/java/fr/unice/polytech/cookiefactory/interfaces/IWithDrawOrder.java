package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.OrderNotFoundException;

public interface IWithDrawOrder {

    public void withDrawOrder(Order order) throws OrderNotFoundException ;
}
