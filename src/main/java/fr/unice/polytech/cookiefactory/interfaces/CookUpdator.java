package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.store.TimeSlot;

import java.time.DayOfWeek;
import java.util.List;

public interface CookUpdator {
    void update(Cook cook, DayOfWeek day , TimeSlot timeOrder);
    void setCookSchedulerInDay(Cook cook, DayOfWeek day, List<TimeSlot> timeSlots);
    List<TimeSlot> getAvailabiltyInDay(Cook cook, DayOfWeek day);
}
