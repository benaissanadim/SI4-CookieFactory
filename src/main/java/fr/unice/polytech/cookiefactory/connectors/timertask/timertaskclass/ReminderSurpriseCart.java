package fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import fr.unice.polytech.cookiefactory.entities.order.SurpriseCart;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import fr.unice.polytech.cookiefactory.repositories.SurpriseCartRepository;
import fr.unice.polytech.cookiefactory.connectors.timertask.TimerTaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ReminderSurpriseCart extends TimerTask implements ITimerTask {

    private Store store;
    private SurpriseCart surpriseCart;
    private OrderRepository orderRepository;
    private SurpriseCartRepository surpriseCartRepository;
    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);


    /**
     * constructor
     */
    public ReminderSurpriseCart(Store store, OrderRepository orderRepository,
            SurpriseCartRepository surpriseCartRepository) {
        this.store = store;
        surpriseCart = new SurpriseCart();
        this.orderRepository = orderRepository;
        this.surpriseCartRepository = surpriseCartRepository;
    }

    /**
     * execute order process
     */
    @Override
    public void run() {

        List<Order> orders = orderRepository.findByStatus(OrderStatus.OBSOLETE);

        if(!orders.isEmpty()) {
            LocalTime localTime = LocalTime.now();
            LocalDate localDate = LocalDate.now();

            if (store.getTimeTable().get(localDate.getDayOfWeek()).getBegin().isBefore(localTime)
                    && store.getTimeTable().get(localDate.getDayOfWeek()).getEnd().isAfter(localTime)) {
                this.surpriseCart = new SurpriseCart(store);
                this.surpriseCart.setSurpriseCookies(getCookiesFromOrders(orders));

                surpriseCartRepository.save(surpriseCart, surpriseCart.getId());
                logger.info("Order is now on surprise cart in the store" + store.getName());
            } else {
                TimerTaskManager.getInstance().deleteTimerTaskByClassAndOrderIfExist(store, this.getClass());
            }
        }

    }

    /**
     * start the timer to execute the process when the time to take the order is over
     */
    @Override
    public void start() throws TimerTaskException {
        long threeHoursPeriod = (long) Math.pow(1.08,7);
        Duration startPeriodInThreeHours = Duration.between(LocalDateTime.now(),LocalDateTime.now().plusHours(3));
        TimerTaskManager.getInstance().createNewTimerTask(this,store,startPeriodInThreeHours.toMillis(),threeHoursPeriod);
    }

    /**
     * Retrieve all the cookies from orders
     */
    public List<Item> getCookiesFromOrders(List<Order> orders) {
        List<Item> cookies = new ArrayList<>();
        for (Order order : orders) {
            cookies.addAll(order.getItems());
        }
        return cookies;
    }

}
