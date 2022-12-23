package fr.unice.polytech.cookiefactory.connectors.timertask;


import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ITimerTask;

import java.lang.reflect.InvocationTargetException;

public class NotificationTask {
    private static NotificationTask instance;

    /**
     * constructor
     */
    private NotificationTask() {
    }

    /**
     * static to get singleton
     * @return instance
     */
    public static NotificationTask getInstance() {
        if (instance == null) {
            instance = new NotificationTask();
        }
        return instance;
    }

    public void notifyClient(Order order, Class classTimerTask) throws TimerTaskException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ITimerTask timerTask = (ITimerTask) classTimerTask.getDeclaredConstructor(Order.class).newInstance(order);
        timerTask.start();
    }

    public void cancelNotification(Order order,Class classTimerTask){
        TimerTaskManager.getInstance().deleteTimerTaskByClassAndOrderIfExist(order,classTimerTask);
    }
}
