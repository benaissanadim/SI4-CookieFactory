package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.order.Order;

public interface OrderPriceCalculator {

    double DISCOUNT = 0.1;

    double calculatePriceHT(Order order);
    double calculatePriceTTC(Order order);
    double calculatePriceFinal(Order order);
}
