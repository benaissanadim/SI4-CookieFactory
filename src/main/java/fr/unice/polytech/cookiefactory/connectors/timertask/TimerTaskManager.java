package fr.unice.polytech.cookiefactory.connectors.timertask;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * a class that manage NotificationTask
 */
public class TimerTaskManager {

    private static TimerTaskManager instance;
    private HashMap<TimerTask, Order> listTimerTaskWithOrderLinked;
    private HashMap<TimerTask, Store> listTimerTaskWithStoreLinked;
    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);


    /**
     * constructor
     */
    private TimerTaskManager() {
        listTimerTaskWithOrderLinked = new HashMap<>();
        listTimerTaskWithStoreLinked = new HashMap<>();
    }

    /**
     * static to get singleton
     * @return instance
     */
    public static TimerTaskManager getInstance() {
        if (instance == null) {
            instance = new TimerTaskManager();
        }
        return instance;
    }

    /**
     * create new Timer Task with speciic delay and period and name to easily find it
     * @param timerTask
     * @param obj
     * @param delay
     * @param period
     */
    public void createNewTimerTask(TimerTask timerTask, Object obj, long delay, long period) throws TimerTaskException {
        if (findTimerTaskByClassAndOrder(obj,timerTask.getClass())==null) {
            Timer timer = new Timer();
            if (period>0) {
                timer.schedule(timerTask, delay, period);
            }
            else{
                logger.info("TimerTaskManager:CreateNewTimerTask:TimerTask with no delay");
                timer.schedule(timerTask, delay);
            }
            if (obj instanceof Order){
                listTimerTaskWithOrderLinked.put(timerTask,(Order)obj);
            }
            else if (obj instanceof Store){
                listTimerTaskWithStoreLinked.put(timerTask,(Store)obj);
            }
            timer.cancel();
        }
        else{
            throw new TimerTaskException("Error : Same notification already exist");
        }
    }


    /**
     * method to find TimerTask with order and class
     * @param obj
     * @param classTimerTask
     * @return TimerTask or null
     */
    public TimerTask findTimerTaskByClassAndOrder(Object obj,Class classTimerTask){
        if(obj instanceof Order){
            Order order = (Order) obj;
            return listTimerTaskWithOrderLinked.entrySet().stream().filter(x -> classTimerTask.isInstance(x.getKey()) && x.getValue().equals(order))
                    .map(Map.Entry::getKey).toList().stream().findFirst().orElse(null);
        }else if(obj instanceof Store){
            Store store = (Store) obj;
            return listTimerTaskWithStoreLinked.entrySet().stream().filter(x -> classTimerTask.isInstance(x.getKey()) && x.getValue().equals(store))
                    .map(Map.Entry::getKey).toList().stream().findFirst().orElse(null);
        } else {
            return null;
        }

    }

    /**
     * check if timerTaskExist in list
     * @param timerTask
     * @return boolean
     */
    private boolean timerTaskExist(TimerTask timerTask){
        return listTimerTaskWithOrderLinked.containsKey(timerTask) || listTimerTaskWithStoreLinked.containsKey(timerTask);
    }

    /**
     * method that cancel timertask and delete it from list notification
     * @param timerTask
     */
    private void deleteTimerTask(TimerTask timerTask){
        if (timerTaskExist(timerTask)) {
            timerTask.cancel();
            listTimerTaskWithOrderLinked.remove(timerTask);
            listTimerTaskWithStoreLinked.remove(timerTask);
        }
    }

    /**
     * delete TimerTask By Class And Order If Exist
     * @param obj
     * @param classTimerTask
     */
    public void deleteTimerTaskByClassAndOrderIfExist(Object obj,Class classTimerTask){
        TimerTask timerTask = findTimerTaskByClassAndOrder(obj,classTimerTask);
        deleteTimerTask(timerTask);
    }
}