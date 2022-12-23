
package fr.unice.polytech.cookiefactory.cucumber;


import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.DayTimeTable;
import fr.unice.polytech.cookiefactory.entities.store.TimeSlot;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.StoreClosedException;
import fr.unice.polytech.cookiefactory.exceptions.StoreNotFoundException;
import fr.unice.polytech.cookiefactory.interfaces.IChooseStoreTime;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ChooseTimeToPickDefs {


    private Store store;
    private Order o1;

    @Autowired
    private IChooseStoreTime chooseStoreTime;

    @Autowired
    private StoreRepository storeRepository;

    @Given("a store with name {string} and address {string}")
    public void aShopWithNameAndAddress(String name, String adress) {
        store = new Store();
        store.setName(name);
        store.setAddress(adress);
    }

    @And("timetable : {string} from {int}:{int} to {int}:{int}, {string} from {int}:{int} to {int}:{int}, {string} from {int}:{int} to {int}:{int}")
    public void timetableFromToFromToFromTo(String arg0, int arg1, int arg2, int arg3, int arg4, String arg5, int arg6, int arg7, int arg8, int arg9,
            String arg10, int arg11, int arg12, int arg13, int arg14) {
        store.setTimeTable(new DayTimeTable());
        store.getTimeTable().put(DayOfWeek.valueOf(arg0),new TimeSlot(LocalTime.of(arg1,arg2), LocalTime.of(arg3,arg4)));
        store.getTimeTable().put(DayOfWeek.valueOf(arg5),new TimeSlot(LocalTime.of(arg6,arg7), LocalTime.of(arg8,arg9)));
        store.getTimeTable().put(DayOfWeek.valueOf(arg10),new TimeSlot(LocalTime.of(arg11,arg12), LocalTime.of(arg13,arg14)));
    }

    @And("a client with order")
    public void aClientWithOrder() {
        Customer client = mock(Customer.class);
        o1 = new Order();
        o1.setCustomer(client);
    }
    
    @When("the client choose to pick the order from store")
    public void theClientChooseToPickTheOrderFromStore() throws StoreNotFoundException {
        storeRepository.save(store, UUID.randomUUID());
        o1.setStore(store);
    }

    @And("choose time on {int}|{int}|{int} at {int}:{int}")
    public void chooseTimeOnAt(int arg0, int arg1, int arg2, int arg3, int arg4)
            throws StoreNotFoundException, StoreClosedException {
        chooseStoreTime.choosePickUpTime(o1, LocalDateTime.of(arg2,arg1,arg0,arg3,arg4));
    }

    @Then("the order is set to pick {int}|{int}|{int}, {int}:{int}")
    public void theOrderIsSetToPick(int arg0, int arg1, int arg2, int arg3, int arg4) {
        assertEquals(LocalDateTime.of(arg2,arg1,arg0,arg3,arg4), o1.getPickUpDateTime());
    }

    @Then("the order can't be set up and exception is thrown")
    public void theOrderCanTBeSetUpAndExceptionIsThrown() {
        assertNull(o1.getPickUpDateTime());
    }

    @And("choose {int}|{int}|{int} at {int}:{int}")
    public void choose(int arg0, int arg1, int arg2, int arg3, int arg4) {
        assertThrows(StoreClosedException.class , () ->
                chooseStoreTime.choosePickUpTime(o1,LocalDateTime.of(arg2,arg1,arg0,arg3,arg4)) );
    }

}