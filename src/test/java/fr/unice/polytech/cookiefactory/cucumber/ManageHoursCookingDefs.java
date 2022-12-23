package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.entities.store.DayTimeTable;
import fr.unice.polytech.cookiefactory.entities.store.TimeSlot;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.interfaces.IStoreAvailable;
import fr.unice.polytech.cookiefactory.interfaces.IStoreScheduler;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManageHoursCookingDefs {
    private Store store;
    private Store store2;
    private DayTimeTable dayTimeTable;
    private DayTimeTable dayTimeTable2 ;

    @Autowired
    IStoreAvailable storeAvailable ;

    @Autowired
    IStoreScheduler storeScheduler ;

    @Given("I'm a store manager")
    public void I_m_a_store_manager() {

    }

    @And("I want to manage store scheduler for Monday")
    public void i_want_to_manage_store_scheduler() {
        store = new Store("Casino", "1 rue de la paix", 8.9);
    }



    @Then("I should see the store hours for Monday")
    public void the_store_has_an_opening_and_closing_time() {
        TimeSlot dayStore = store.getTimeTable().get(DayOfWeek.MONDAY);
        assertNotNull(dayStore);
        assertEquals(LocalTime.of(9, 0), dayStore.getBegin());
        assertEquals(LocalTime.of(18, 0), dayStore.getEnd());
    }


    @And("I want to manage store scheduler for a week")
    public void i_want_to_manage_store_scheduler1() {
        store2 = new Store("Casino", "1 rue de la paix", 8.9);
    }

    @When("I choose 9 AM  as start time as 6 PM as end time for a week")
    public void i_choose_the_store_timeline1() {
        List<DayOfWeek> days = new ArrayList<>();
        days.add(DayOfWeek.MONDAY);
        days.add(DayOfWeek.TUESDAY);
        days.add(DayOfWeek.WEDNESDAY);
        days.add(DayOfWeek.THURSDAY);
        days.add(DayOfWeek.FRIDAY);
        days.add(DayOfWeek.SATURDAY);
        days.add(DayOfWeek.SUNDAY);
        storeScheduler.setDaysSchedule(store2 , days, LocalTime.of(9, 0), LocalTime.of(18, 0));

    }

    @Then("I should see the store hours for a week")
    public void the_store_has_an_opening_and_closing_time1() {
        TimeSlot dayStore = store2.getTimeTable().get(DayOfWeek.TUESDAY);
        TimeSlot dayStore2 = store2.getTimeTable().get(DayOfWeek.WEDNESDAY);
        TimeSlot dayStore3 = store2.getTimeTable().get(DayOfWeek.THURSDAY);
        TimeSlot dayStore4 = store2.getTimeTable().get(DayOfWeek.FRIDAY);
        assertNotNull(dayStore);
        assertNotNull(dayStore2);
        assertNotNull(dayStore3);
        assertNotNull(dayStore4);
        //Tuesday
        assertEquals(LocalTime.of(9, 0), dayStore.getBegin());
        assertEquals(LocalTime.of(18, 0), dayStore.getEnd());
        //Wednesday
        assertEquals(LocalTime.of(9, 0), dayStore2.getBegin());
        assertEquals(LocalTime.of(18, 0), dayStore2.getEnd());
        //Thursday
        assertEquals(LocalTime.of(9, 0), dayStore3.getBegin());
        assertEquals(LocalTime.of(18, 0), dayStore3.getEnd());
    }


    @Given("I am a store manager")
    public void iAmAStoreManager() {
    }

    @When("I want to manage store scheduler")
    public void iWantToManageStoreScheduler() {
        store = new Store();
        dayTimeTable = new DayTimeTable();
        store.setTimeTable(dayTimeTable);
    }

    @And("I choose {int}:{int}  as start time as {int}:{int} as end time for {string}")
    public void iChooseAsStartTimeAsAsEndTimeFor(int arg0, int arg1, int arg2, int arg3, String arg4) {
        storeScheduler.setDayTimeTable(store , DayOfWeek.valueOf(arg4), LocalTime.of(arg0, arg1), LocalTime.of(arg2, arg3));
    }


    @Then("I should see the store hours {int}:{int} to {int}:{int} for {string}")
    public void iShouldSeeTheStoreHoursToFor(int arg0, int arg1, int arg2, int arg3, String arg4) {
        TimeSlot dayStore = store.getTimeTable().get(DayOfWeek.valueOf(arg4));
        assertNotNull(dayStore);
        assertEquals(LocalTime.of(arg0, arg1), dayStore.getBegin());
        assertEquals(LocalTime.of(arg2, arg3), dayStore.getEnd());
    }

    @And("I choose {int}:{int}  as start time as {int}:{int} as end time for a week")
    public void iChooseAsStartTimeAsAsEndTimeForAWeek(int arg0, int arg1, int arg2, int arg3) {
        List<DayOfWeek> days = new ArrayList<>();
        days.add(DayOfWeek.MONDAY);
        days.add(DayOfWeek.TUESDAY);
        days.add(DayOfWeek.WEDNESDAY);
        days.add(DayOfWeek.THURSDAY);
        days.add(DayOfWeek.FRIDAY);
        days.add(DayOfWeek.SATURDAY);
        days.add(DayOfWeek.SUNDAY);
        storeScheduler.setDaysSchedule(store , days, LocalTime.of(arg0, arg1), LocalTime.of(arg2, arg3));
    }



    @Then("I should see the store hours for {string} {string} {string} {string} {string} {string} {string} from {int}:{int} to {int}:{int}")
    public void iShouldSeeTheStoreHoursForFromTo(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5,
                                                 String arg6, int arg7, int arg8, int arg9, int arg10) {
        TimeSlot dayStore0 = store.getTimeTable().get(DayOfWeek.valueOf(arg0));
        TimeSlot dayStore1 = store.getTimeTable().get(DayOfWeek.valueOf(arg1));
        TimeSlot dayStore2 = store.getTimeTable().get(DayOfWeek.valueOf(arg2));
        TimeSlot dayStore3 = store.getTimeTable().get(DayOfWeek.valueOf(arg3));
        TimeSlot dayStore4 = store.getTimeTable().get(DayOfWeek.valueOf(arg4));
        TimeSlot dayStore5 = store.getTimeTable().get(DayOfWeek.valueOf(arg5));
        TimeSlot dayStore6 = store.getTimeTable().get(DayOfWeek.valueOf(arg6));

        assertNotNull(dayStore0);
        assertNotNull(dayStore1);
        assertNotNull(dayStore2);
        assertNotNull(dayStore3);
        assertNotNull(dayStore4);
        assertNotNull(dayStore5);
        assertNotNull(dayStore6);

        //Monday
        assertEquals(LocalTime.of(arg7, arg8), dayStore0.getBegin());
        assertEquals(LocalTime.of(arg9, arg10), dayStore0.getEnd());
        //Tuesday
        assertEquals(LocalTime.of(arg7, arg8), dayStore1.getBegin());
        assertEquals(LocalTime.of(arg9, arg10), dayStore1.getEnd());
        //Wednesday
        assertEquals(LocalTime.of(arg7, arg8), dayStore2.getBegin());
        assertEquals(LocalTime.of(arg9, arg10), dayStore2.getEnd());
        //Thursday
        assertEquals(LocalTime.of(arg7, arg8), dayStore3.getBegin());
        assertEquals(LocalTime.of(arg9, arg10), dayStore3.getEnd());
        //Friday
        assertEquals(LocalTime.of(arg7, arg8), dayStore4.getBegin());
        assertEquals(LocalTime.of(arg9, arg10), dayStore4.getEnd());
        //Saturday
        assertEquals(LocalTime.of(arg7, arg8), dayStore5.getBegin());
        assertEquals(LocalTime.of(arg9, arg10), dayStore5.getEnd());
        //Sunday
        assertEquals(LocalTime.of(arg7, arg8), dayStore6.getBegin());
        assertEquals(LocalTime.of(arg9, arg10), dayStore6.getEnd());


    }
}