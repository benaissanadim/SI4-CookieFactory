package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;
import fr.unice.polytech.cookiefactory.interfaces.IEndCookingOrder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateEndCookingDefs {
    private Order order;

    @Autowired IEndCookingOrder endCookingOrder;

    @Given("a cook affected to the order") public void aCookAffectedToTheOrder() {
        Cook cook = new Cook();
    }

    @Given("order with status COOKING") public void orderWithStatusCOOKING() {
        order = new Order();
        order.setOrderState(new OrderState());
        order.getOrderState().next();
        order.getOrderState().next();
    }

    @When("cook validate the end of cooking the order") public void cookValidateTheEndOfCookingTheOrder()
            throws TimerTaskException, ClassNotFoundException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        order.setPickUpDateTime(LocalDateTime.now().plusHours(1));
        endCookingOrder.endCookingOrder(order);
    }

    @Then("the status order is READY") public void theStatusOrderIsREADY() {
        assertEquals(OrderStatus.READY, order.getOrderState().getStatus());
    }

    @Given("order with status PLACED") public void orderWithStatusPLACED() {
        order = new Order();
        order.setOrderState(new OrderState());
        order.setPickUpDateTime(LocalDateTime.now().plusHours(1));
        order.getOrderState().next();
    }

    @Then("the status order is PLACED") public void theStatusOrderIsPLACED() {
        assertEquals(OrderStatus.PLACED, order.getOrderState().getStatus());

    }

    @When("the cook validate the end of cooking the order") public void theCookValidateTheEndOfCookingTheOrder()
            throws TimerTaskException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        order.setPickUpDateTime(LocalDateTime.now().plusHours(1));
        endCookingOrder.endCookingOrder(order);
    }
}