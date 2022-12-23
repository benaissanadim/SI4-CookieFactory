package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import fr.unice.polytech.cookiefactory.interfaces.OrderTimePreparationCalculator;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.interfaces.LoyaltyProgramBenefits;
import fr.unice.polytech.cookiefactory.interfaces.OrderPriceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class OrderCalculatorService implements OrderPriceCalculator, OrderTimePreparationCalculator {

    LoyaltyProgramBenefits loyaltyProgramBenefits;
    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);



    @Autowired
    public OrderCalculatorService(LoyaltyProgramBenefits loyaltyProgramBenefits){
        this.loyaltyProgramBenefits = loyaltyProgramBenefits;
    }

    @Override
    public double calculatePriceHT(Order order){
        return order.getItems().stream()
                    .mapToDouble(item -> item.getQuantity()*item.getCookie().getPrice())
                    .sum();
    }

    public double calculatePriceTTC(Order order){
        Store store = order.getStore();
        if(store == null) return calculatePriceHT(order);
        return calculatePriceHT(order)*(1+(store.getTaxPercentage())/100);
    }

    public double calculatePriceFinal(Order order){
        Customer customer = order.getCustomer();
        double price = calculatePriceTTC(order);
        if(customer == null) return calculatePriceTTC(order);
        return loyaltyProgramBenefits.eligibleToDiscount(customer) ? price-(price*DISCOUNT) : price;
    }

    @Override
    public long calculatePreparationTime(Order order) {
        return  order.getItems().stream()
                    .mapToLong(item -> item.getCookie().getPreparationTime() * item.getQuantity())
                    .sum();
    }

    @Override
    public long calculatePrepTimeForCook(Order order){
        long prepTime = calculatePreparationTime(order);
        int rest = (int) prepTime % BLOCK_TIME;
        prepTime += BLOCK_TIME;
        if(rest !=0){
            prepTime+= BLOCK_TIME - rest;
        }
        logger.info("preparation time "+prepTime);
        return prepTime;
    }

    @Override
    public LocalTime getBeginPrepTime(Order order){
        return order.getPickUpDateTime().minusMinutes(this.calculatePrepTimeForCook(order)).toLocalTime();
    }

}
