package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.components.order.WithDrawOrderService;
import fr.unice.polytech.cookiefactory.connectors.state.CookingState;
import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.connectors.timertask.TimerTaskManager;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ObsoleteOrder;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderAfter1Hour;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderAfter5Minutes;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderSurpriseCart;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.OrderNotFoundException;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;
import fr.unice.polytech.cookiefactory.interfaces.IEndCookingOrder;
import fr.unice.polytech.cookiefactory.interfaces.IWithDrawOrder;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import fr.unice.polytech.cookiefactory.repositories.SurpriseCartRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.lang.reflect.InvocationTargetException;
import java.time.*;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TimerTasksDefs {

    private Order order;
    private Class classTimerTask;
    private Store store;


    @Autowired
    IEndCookingOrder endCookingOrder ;

    @Given("order with status the COOKING")
    public void orderWithStatus() {
        order = new Order();
        OrderState orderState = new OrderState();
        orderState.setStatus(OrderStatus.COOKING);
        orderState.setState(new CookingState());
        order.setOrderState(orderState);
        order.setPickUpDateTime(LocalDateTime.now().plusHours(1));
        theStatusOrderIs("COOKING");
    }

    @When("a cook validate the end of cooking the order")
    public void cookValidateTheEndOfCookingTheOrder() throws TimerTaskException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        order.setPickUpDateTime(LocalDateTime.now());
        endCookingOrder.endCookingOrder(order);
    }

    @Then("status of the order is {string}")
    public void theStatusOrderIs(String arg0) {
        Assertions.assertEquals(OrderStatus.valueOf(arg0),order.getOrderState().getStatus());
    }

    @Then("timerTask obsoleteOrder is created")
    public void timertaskObsoleteOrderCreated() {
        System.out.println(order);
        System.out.println(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(order, ObsoleteOrder.class));
        classTimerTask = ObsoleteOrder.class;
        Assertions.assertNotNull(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(order, classTimerTask));
    }

    @Then("timerTask reminderAfter5Minutes is created")
    public void timertaskReminderAfter5MinutesCreated() {
        classTimerTask = ReminderAfter5Minutes.class;
        Assertions.assertNotNull(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(order, classTimerTask));
    }

    @Then("timerTask reminderAfter1Hour is created")
    public void timertaskReminderAfter1HourCreated() {
        classTimerTask = ReminderAfter1Hour.class;
        Assertions.assertNotNull(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(order, classTimerTask));
    }

    @When("timerTask reminderSurpriseCart is created")
    public void timertaskReminderSurpriseCartCreated() {
        classTimerTask = ReminderSurpriseCart.class;
        Assertions.assertNotNull(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(store, classTimerTask));
    }

    @When("a cashier validate withdraw of the order")
    public void aCashierValidateWithdrawOfTheOrder() throws OrderNotFoundException {
        OrderRepository orderRepository = mock(OrderRepository.class);
        when(orderRepository.existsById(any())).thenReturn(true);
        IWithDrawOrder withDrawOrder = new WithDrawOrderService(orderRepository);
        withDrawOrder.withDrawOrder(order);
    }

    @Then("timerTask obsoleteOrder is deleted")
    public void timertaskObsoleteOrderIsDeleted() {
        System.out.println(order);
        System.out.println(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(order, ObsoleteOrder.class));
        Assertions.assertNull(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(order, ObsoleteOrder.class));
    }

    @Then("timerTask reminderAfter5Minutes is deleted")
    public void timertaskReminderAfter5MinutesIsDeleted() {
        Assertions.assertNull(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(order, ReminderAfter5Minutes.class));
    }

    @Then("timerTask reminderAfter1Hour is deleted")
    public void timertaskReminderAfter1HourIsDeleted() {
        Assertions.assertNull(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(order, ReminderAfter1Hour.class));
    }

    @Then("timerTask reminderSurpriseCart is not deleted")
    public void timertaskReminderSurpriseCartIsNotDeleted() {
        Assertions.assertNotNull(TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(store, ReminderSurpriseCart.class));
    }

    @And("{int} minutes have passed")
    public void hoursHavePassed(int delay) {
        Object orderOrStore = order==null ? store : order;
        TimerTask timerTask = TimerTaskManager.getInstance().findTimerTaskByClassAndOrder(orderOrStore, classTimerTask);
        Assertions.assertNotNull(timerTask);
        long nextExecutionTimeTask = timerTask.scheduledExecutionTime();
        System.out.println(nextExecutionTimeTask);
        Assertions.assertTrue(nextExecutionTimeTask>System.currentTimeMillis());
        LocalTime timeToPickUpOrderOrNowIfStore = order==null ? LocalTime.now() : order.getPickUpDateTime().toLocalTime();
        long nextExecutionTimeTaskDelay = nextExecutionTimeTask - TimeUnit.SECONDS.toMillis(timeToPickUpOrderOrNowIfStore.toEpochSecond(LocalDate.now(), OffsetDateTime.now().getOffset()));
        System.out.println(Math.abs(nextExecutionTimeTaskDelay - TimeUnit.MINUTES.toMillis(delay)));
        Assertions.assertTrue(Math.abs(nextExecutionTimeTaskDelay - TimeUnit.MINUTES.toMillis(delay))<60000); //plus ou moins 1 minute
        if (order!=null) {
            Store store = new Store();
            order.setStore(store);
        }
        timerTask.run();
    }

    @Given("cook affected to the order")
    public void cookAffectedToTheOrder() {
    }

    @Given("cashier")
    public void cashier() {
    }

    @Given("TooGoodToGo Surprise Cart Task started")
    public void toogoodtogoSurpriseCartTaskStarted() throws TimerTaskException {
        store = new Store();
        TimerTaskManager.getInstance().deleteTimerTaskByClassAndOrderIfExist(store,ReminderSurpriseCart.class);
        ReminderSurpriseCart reminderSurpriseCart = new ReminderSurpriseCart(store,new OrderRepository(),new SurpriseCartRepository());
        reminderSurpriseCart.start();
    }

}