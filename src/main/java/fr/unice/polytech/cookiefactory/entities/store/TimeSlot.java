package fr.unice.polytech.cookiefactory.entities.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * class that represents the timetable of the store
 */
@Getter
@AllArgsConstructor
public class TimeSlot {

    private LocalTime begin;
    private LocalTime end;

    public Optional<List<TimeSlot>> retrieve(TimeSlot time) {
        List<TimeSlot> times = new ArrayList<>();
        if(time.getBegin().equals(begin) && time.getEnd().isBefore(end)){
            times.add(new TimeSlot(time.getEnd(), end));
            return Optional.of(times);
        }
        else if(time.getEnd().equals(end) && time.getBegin().isAfter(begin)){
            times.add(new TimeSlot(begin, time.getEnd()));
            return Optional.of(times);
        }
        else if(time.getBegin().isAfter(begin) && time.getEnd().isBefore(end)){
            times.add(new TimeSlot(begin, time.getBegin()));
            times.add(new TimeSlot(time.getEnd(), end));
            return Optional.of(times);
        }

        return Optional.empty();
    }

    public boolean contains(TimeSlot time){
        return (time.getBegin().isAfter(begin) || time.getBegin().equals(begin))
                && (time.getEnd().isBefore(end) || time.getEnd().equals(end)) ;
    }

}
