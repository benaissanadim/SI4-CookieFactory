package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.store.Event;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.SpecialOrder;
import fr.unice.polytech.cookiefactory.entities.store.SpecialStore;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.StoreNotFoundException;
import fr.unice.polytech.cookiefactory.interfaces.IChooseStoreTime;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChooseSimpleSpecialStoreDefs {

    private Order order;
    private SpecialOrder order2;
    private Store store1;
    private SpecialStore store2;
    private String msgException;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private IChooseStoreTime chooseStoreTime;

    @Given("a factory with stores {string} with address {string}")
    public void aFactoryWithStoresWithAdress(String arg0, String arg1) {
        store1 = mock(Store.class);
        when(store1.getName()).thenReturn(arg0);
        when(store1.getAddress()).thenReturn(arg1);
        when(store1.getId()).thenReturn(UUID.randomUUID());
        storeRepository.save(store1, store1.getId());
    }

    @And("special store {string} with events {string} and {string} with address {string}")
    public void specialStoreWithEventsAndWithAddress(String arg0, String arg1, String arg2, String arg3) {
        store2 = mock(SpecialStore.class);
        when(store2.getName()).thenReturn(arg0);
        when(store2.getAddress()).thenReturn(arg1);
        when(store2.getId()).thenReturn(UUID.randomUUID());
        List<Event> eventList = new ArrayList<>();
        eventList.add(Event.valueOf(arg1));
        eventList.add(Event.valueOf(arg2));
        when(store2.getEvents()).thenReturn(eventList);
        storeRepository.save(store2,store2.getId());
    }

    @And("I am a client having an order")
    public void iAmAClientHavingAnOrder() {
        order = new Order();
        Customer customer = mock(Customer.class);
        order.setCustomer(customer);
    }

    @When("I want to choose the store {string}")
    public void iWantToChooseTheStore(String arg0) {
        try {
            chooseStoreTime.chooseStore(order, store1);
        }catch (StoreNotFoundException e){
            msgException = e.getMessage();
        }
    }

    @Then("the store is assigned to the order")
    public void theStoreIsAssignedToTheOrder() {
        assertEquals(store1,order.getStore());
    }


    @Then("the store is not assigned to the order") public void theStoreIsNotAssignedToTheOrder() {
        assertNull(order.getStore());
    }

    @Then("exception {string}")
    public void exception(String arg0) {
        assertEquals(arg0, msgException);
    }

    @Given("Order of event {string}")
    public void orderOfEvent(String arg0) {
        order2 = new SpecialOrder();
        order2.setEvent(Event.valueOf(arg0));
    }

    @When("I want to choose the store {string} for the special order")
    public void iWantToChooseTheStoreForTheSpecialOrder(String arg0) {

        try {
            chooseStoreTime.chooseStore(order2, store2);
        }catch (StoreNotFoundException e){
            msgException = e.getMessage();
        }

    }

    @Then("the store {string} is assigned to the special order")
    public void theStoreIsAssignedToTheSpecialOrder(String arg0) {
        assertEquals(arg0,order2.getStore().getName());
    }

    @When("I want to choose the store not in the system")
    public void iWantToChooseTheStoreNotInTheSystem() {
        Store store = new Store();
        store.setId(UUID.randomUUID());
        try {
            chooseStoreTime.chooseStore(order, store);
        }catch (StoreNotFoundException e){
            msgException = e.getMessage();
        }
    }
}

