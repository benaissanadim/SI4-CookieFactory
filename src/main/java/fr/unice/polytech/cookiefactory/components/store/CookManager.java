package fr.unice.polytech.cookiefactory.components.store;

import fr.unice.polytech.cookiefactory.components.order.OrderCalculatorService;
import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.TimeSlot;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.repositories.CookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Component
public class CookManager {

    OrderCalculatorService orderCalculatorService;
    CookRepository cookRepository ;
    CookService cookService;

    @Autowired
    public CookManager(CookRepository cookRepository , CookService cookService,OrderCalculatorService orderCalculatorService) {
        this.cookRepository = cookRepository ;
        this.cookService = cookService ;
        this.orderCalculatorService= orderCalculatorService;
    }


    public Optional<Cook> findCook(Order order) {
        Store store = order.getStore();
        List<Cook> cooks = store.getCooks() ;
        for (Cook cook : cooks) {
            DayOfWeek day = order.getPickUpDateTime().getDayOfWeek();
            List<TimeSlot> slots = cookService.getAvailabiltyInDay(cook, day);
            TimeSlot time = new TimeSlot(orderCalculatorService.getBeginPrepTime(order),
                    order.getPickUpDateTime().toLocalTime());

            for (TimeSlot timeSlot : slots) {
                if (timeSlot.contains(time)) {
                    return Optional.of(cook);
                }
            }
        }
        return Optional.empty();
    }

    public void affectCookToOrder(Cook cook, Order order){
        DayOfWeek day = order.getPickUpDateTime().getDayOfWeek();
        TimeSlot timeSlot = new TimeSlot(orderCalculatorService.getBeginPrepTime(order),
                order.getPickUpDateTime().toLocalTime());
        cookService.update(cook, day, timeSlot);
        order.setCook(cook);
    }



}
