package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.exceptions.OrderNotFoundException;
import fr.unice.polytech.cookiefactory.interfaces.IWithDrawOrder;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import fr.unice.polytech.cookiefactory.connectors.timertask.NotificationTask;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ObsoleteOrder;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderAfter1Hour;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderAfter5Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WithDrawOrderService implements IWithDrawOrder {

    OrderRepository orderRepository ;

    @Autowired
    public WithDrawOrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository ;
    }


    public void withDrawOrder(Order order) throws OrderNotFoundException {
        if(!orderRepository.existsById(order.getId())){
            throw new OrderNotFoundException(" ticket order not found");
        }
        if(!order.getOrderState().getStatus().equals(OrderStatus.READY)){
            throw new OrderNotFoundException("order is not ready yet ");
        }
        order.getOrderState().next();
        NotificationTask.getInstance().cancelNotification(order, ObsoleteOrder.class);
        NotificationTask.getInstance().cancelNotification(order, ReminderAfter5Minutes.class);
        NotificationTask.getInstance().cancelNotification(order, ReminderAfter1Hour.class);

    }
}
