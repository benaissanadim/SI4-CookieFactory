package fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass;


import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;
import fr.unice.polytech.cookiefactory.connectors.timertask.TimerTaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimerTask;

/**
 * a class that represents obsolete order process
 */
public class ReminderAfter5Minutes extends TimerTask implements ITimerTask {

    private Order order;
    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);


    /**
     * constructor
     * @param order that has to be obsolete
     */
    public ReminderAfter5Minutes(Order order) {
        this.order = order;
    }

    /**
     * execute order process
     */
    @Override
    public void run() {
        logger.info("Your order is ready, you need to withdraw it, in 1h55 this order will be OBSOLETE");
        TimerTaskManager.getInstance().deleteTimerTaskByClassAndOrderIfExist(order, this.getClass());
    }

    /**
     * start the timer to execute the process when the time to take the order is over
     */
    @Override
    public void start() throws TimerTaskException {
        LocalDateTime localTime = LocalDateTime.now();
        LocalDateTime timeToRunReminder = order.getPickUpDateTime().plusMinutes(5);
        Duration duration = Duration.between(localTime,timeToRunReminder);
        logger.info(localTime.toString());
        logger.info(timeToRunReminder.toString());
        logger.info(duration.toMillis()+"");
        long delay = duration.toMillis();
        if (delay<0)
            throw new TimerTaskException("Error : le delay est negatif");
        else
            TimerTaskManager.getInstance().createNewTimerTask(this,order,delay,0);
    }

}
