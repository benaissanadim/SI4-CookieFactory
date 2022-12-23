package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;

import java.lang.reflect.InvocationTargetException;

public interface IStartCookingOrder {

    public void startCookingOrder(Order order) throws  StockException, TimerTaskException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException  ;


}
