package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.store.Store;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface IStoreScheduler {

    public void setDayTimeTable(Store store , DayOfWeek day, LocalTime opening, LocalTime closing);


    public void setDaysSchedule(Store store , List<DayOfWeek> days, LocalTime opening, LocalTime closing);
}
