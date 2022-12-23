package fr.unice.polytech.cookiefactory.components.store;


import fr.unice.polytech.cookiefactory.interfaces.IStoreScheduler;
import fr.unice.polytech.cookiefactory.entities.store.DayTimeTable;
import fr.unice.polytech.cookiefactory.entities.store.TimeSlot;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.interfaces.IStoreAvailable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class StoreManager implements IStoreAvailable , IStoreScheduler {
    @Override
    public void setDayTimeTable(Store store, DayOfWeek day, LocalTime opening, LocalTime closing) {
        store.getTimeTable().put(day, new TimeSlot(opening, closing));
    }

    @Override
    public void setDaysSchedule(Store store, List<DayOfWeek> days, LocalTime opening, LocalTime closing) {
        for (DayOfWeek value : days) {
            store.getTimeTable().put(value, new TimeSlot(opening, closing));
        }
    }

    @Override
    public boolean isAvailable(Store store, LocalDateTime dateTime) {
        DayTimeTable dayTimeTable = store.getTimeTable();
        DayOfWeek day = DayOfWeek.from(dateTime.toLocalDate());

        if(!dayTimeTable.containsKey(day)){
            return false;
        }
        TimeSlot timeSlot = dayTimeTable.get(day);
        LocalTime time = dateTime.toLocalTime();
        return time.isAfter(timeSlot.getBegin()) && time.isBefore(timeSlot.getEnd());
    }

}
