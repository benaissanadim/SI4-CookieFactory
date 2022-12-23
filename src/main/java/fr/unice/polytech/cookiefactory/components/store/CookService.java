package fr.unice.polytech.cookiefactory.components.store;

import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.store.TimeSlot;
import fr.unice.polytech.cookiefactory.interfaces.CookUpdator;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Component
public class CookService implements CookUpdator {

    public void update(Cook cook, DayOfWeek day , TimeSlot timeOrder){
        for(TimeSlot timeSlot : cook.getCookScheduler().get(day)){
            Optional<List<TimeSlot>> optTimeSlot = timeSlot.retrieve(timeOrder);
            if(optTimeSlot.isPresent()){
                setCookSchedulerInDay(cook,day,optTimeSlot.get());
                return;
            }
        }
    }

    public void setCookSchedulerInDay(Cook cook, DayOfWeek day, List<TimeSlot> timeSlots){
        cook.getCookScheduler().put(day, timeSlots);
    }

    public List<TimeSlot> getAvailabiltyInDay(Cook cook, DayOfWeek day){
        return cook.getCookScheduler().get(day);
    }

}
