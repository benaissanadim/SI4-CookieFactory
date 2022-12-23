package fr.unice.polytech.cookiefactory.entities.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.HashMap;

/**
 * class that represents a scheduler for each day of the week the dayTimeTable
 */
@Getter
@Setter
@AllArgsConstructor
public class DayTimeTable extends HashMap<DayOfWeek, TimeSlot>{

}
