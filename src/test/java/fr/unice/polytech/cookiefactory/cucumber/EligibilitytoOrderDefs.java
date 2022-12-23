package fr.unice.polytech.cookiefactory.cucumber;


import fr.unice.polytech.cookiefactory.connectors.state.OrderState;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;

import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.interfaces.CustomerEligibility;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EligibilitytoOrderDefs {
    Order o1;
    Order o2;
    Customer client;
    Store store;

    @Autowired
    private CustomerEligibility eligibility;

    @Autowired
    private OrderRepository orderRepository;

    @Given("a client {string}")
    public void aClient(String s) {
        client = mock(Customer.class);
        when(client.getName()).thenReturn(s);
        store = mock(Store.class);
    }

    @And("two orders o1 and o2")
    public void twoOrdersO1AndO2() {
        o1 = new Order();
        o2 = new Order();
        o1.setCustomer(client);
        o1.setId(UUID.randomUUID());
        o2.setCustomer(client);
        o2.setId(UUID.randomUUID());
        OrderState state = new OrderState();
        state.cancel();
        o1.setOrderState(state);
        o2.setOrderState(state);
    }

    @When("the client canceled o1 at {int}:{int} and o2 at {int}:{int}")
    public void theClientCanceledO1AndO2InMinutes( int arg0, int arg1,int arg2, int arg3) {
        orderRepository.deleteAll();
        o1.setStatusDateChange(LocalDateTime.of(LocalDate.now(), LocalTime.of(arg0, arg1)));
        o2.setStatusDateChange(LocalDateTime.of(LocalDate.now(), LocalTime.of(arg2, arg3)));
        orderRepository.save(o1,o1.getId());
        orderRepository.save(o2,o2.getId());

    }

    @Then("he cannot order o3 at {int}:{int}")
    public void heCannotOrder(int arg0, int arg1) {
        Assertions.assertFalse(eligibility.eligibleToOrder(client,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(arg0, arg1))));
    }

    @When("o1 cancelled at {int}h{int}")
    public void order1Cancelled(int arg0, int arg1) {
        orderRepository.deleteAll();
        o1.setStatusDateChange(LocalDateTime.of(LocalDate.now(), LocalTime.of(arg0, arg1)));
        orderRepository.save(o1,o1.getId());
    }
    @And("o2 cancelled at {int}h{int}")
    public void Order2Canceled( int arg0, int arg1) {
        o2.setStatusDateChange(LocalDateTime.of(LocalDate.now(), LocalTime.of(arg0, arg1)));
        orderRepository.save(o2,o2.getId());
    }

    @Then("he can order at {int}h{int}")
    public void heCanOrderAtHJ(int arg0, int arg1) {
        Assertions.assertTrue(eligibility.eligibleToOrder(client,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(arg0, arg1))));
    }



    @Then("he is able to order again at {int}h{int}")
    public void heIsAbleToOrderAgainAtHH(int arg0, int arg1) {
        Assertions.assertTrue(eligibility.eligibleToOrder(client,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(arg0, arg1))));
    }

}
