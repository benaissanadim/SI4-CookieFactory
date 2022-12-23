package fr.unice.polytech.cookiefactory.entities.store.cook;

import fr.unice.polytech.cookiefactory.entities.store.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CookScheduler extends HashMap<DayOfWeek, List<TimeSlot>> {
}
