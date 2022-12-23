package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.order.Order;

import java.time.LocalTime;

public interface OrderTimePreparationCalculator {

    int BLOCK_TIME = 15;

    long calculatePreparationTime(Order order);

    public long calculatePrepTimeForCook(Order order ) ;

    public LocalTime getBeginPrepTime(Order order) ;


}
