package fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;
import fr.unice.polytech.cookiefactory.connectors.timertask.TimerTaskManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimerTask;

/**
 * a class that represents obsolete order process
 */
public class ObsoleteOrder extends TimerTask implements ITimerTask {

    private Order order;

    /**
     * constructor
     * @param order that has to be obsolete
     */
    public ObsoleteOrder(Order order) {
        this.order = order;
    }

    /**
     * execute order process
     */
    @Override
    public void run() {
        if (order.getOrderState().getStatus().equals(OrderStatus.READY)) {
            order.getOrderState().obsolete();
            TimerTaskManager.getInstance().deleteTimerTaskByClassAndOrderIfExist(order, this.getClass());
        }
    }

    /**
     * start the timer to execute the process when the time to take the order is over
     */
    @Override
    public void start() throws TimerTaskException {
        LocalDateTime localTime = LocalDateTime.now();
        LocalDateTime timeToMakeItObsolete =  order.getPickUpDateTime().plusHours(2);
        Duration duration = Duration.between(localTime,timeToMakeItObsolete);
        long delay = duration.toMillis();
        if (delay<0)
            throw new TimerTaskException("Error : le delay est negatif");
        else
            TimerTaskManager.getInstance().createNewTimerTask(this,order,delay,0);
    }


}
