package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.connectors.timertask.NotificationTask;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ObsoleteOrder;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderAfter1Hour;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderAfter5Minutes;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;
import fr.unice.polytech.cookiefactory.interfaces.IEndCookingOrder;
import fr.unice.polytech.cookiefactory.interfaces.IStartCookingOrder;
import fr.unice.polytech.cookiefactory.interfaces.IStockPreparation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class StartEndCookingService implements IStartCookingOrder , IEndCookingOrder {

    IStockPreparation stockPreparation ;
    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);


    @Autowired
    public StartEndCookingService(IStockPreparation stockPreparation){
        this.stockPreparation = stockPreparation ;
    }

    @Override
    public void startCookingOrder(Order order) throws StockException, TimerTaskException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        OrderState orderState = order.getOrderState() ;
        logger.info(orderState.getStatus().toString());
        if (orderState.getStatus().equals(OrderStatus.PLACED)) {
            logger.info("this is my store"+order.getStore().getName());
            stockPreparation.prepare(order.getStore() , order);
            orderState.next();
        }
    }


    @Override
    public void endCookingOrder(Order order) throws TimerTaskException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException{
        OrderState orderState = order.getOrderState() ;
        if (orderState.getStatus().equals(OrderStatus.COOKING)) {
            order.getOrderState().next();
            NotificationTask.getInstance().notifyClient(order, ReminderAfter5Minutes.class);
            NotificationTask.getInstance().notifyClient(order, ReminderAfter1Hour.class);
            NotificationTask.getInstance().notifyClient(order, ObsoleteOrder.class);
        }
    }
}
