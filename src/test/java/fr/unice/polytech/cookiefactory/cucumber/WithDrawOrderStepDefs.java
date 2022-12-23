package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.exceptions.OrderNotFoundException;
import fr.unice.polytech.cookiefactory.interfaces.IWithDrawOrder;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

public class WithDrawOrderStepDefs {

    @Autowired
    OrderRepository orderRepository ;

    @Autowired
    IWithDrawOrder withDrawOrder ;

    Order order;
    Customer client;

    @Given("an  order")
    public void anOrder() {
        order = new Order();

    }

    @And("its order with status READY")
    public void itsOrderWithStatusREADY() {
        order.setOrderState(new OrderState());
        for(int i=0;i<3;i++){
            order.getOrderState().next();
        }
        orderRepository.save(order , order.getId());

    }

    @When("a cashier validate the withdraw of the order")
    public void aCashierValidateTheWithdrawOfTheOrder() throws OrderNotFoundException {
        withDrawOrder.withDrawOrder(order);
    }

    @Then("the status of the order is TAKEN")
    public void theStatusOfTheOrderIsTAKEN() {
        Assertions.assertEquals(OrderStatus.TAKEN , order.getOrderState().getStatus() );
    }

    @Given("another order")
    public void anotherOrder() {
        order = new Order();

    }

    @When("the status of th order is not READY")
    public void theStatusOfThOrderIsNotREADY() {
        order.setOrderState(new OrderState());
        for(int i=0;i<1;i++){
            order.getOrderState().next();
        }
    }

    @Then("a cashier cant validate the withdraw of the order")
    public void aCashierCantValidateTheWithdrawOfTheOrder() throws OrderNotFoundException {
       Assertions.assertThrows(OrderNotFoundException.class,() -> withDrawOrder.withDrawOrder(order)) ;

    }


    @Given("an order o")
    public void anOrderO() {
        order = new Order();
    }

    @And("its order with status COOKING")
    public void itsOrderWithStatusCOOKING() {
        order.setOrderState(new OrderState());
        for(int i=0;i<2;i++){
            order.getOrderState().next();
        }
    }

    @When("cashier validate the withdraw of the order")
    public void cashierValidateTheWithdrawOfTheOrder() throws OrderNotFoundException {
        Assertions.assertThrows(OrderNotFoundException.class,() -> withDrawOrder.withDrawOrder(order)) ;
    }

    @Then("the status of the order is STILL COOKING")
    public void theStatusOfTheOrderIsSTILLCOOKING() {
        Assertions.assertEquals(OrderStatus.COOKING , order.getOrderState().getStatus());
    }





}
