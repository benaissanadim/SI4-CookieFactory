package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;

import java.lang.reflect.InvocationTargetException;

public interface IEndCookingOrder {

    public void endCookingOrder( Order order) throws TimerTaskException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException ;
}
