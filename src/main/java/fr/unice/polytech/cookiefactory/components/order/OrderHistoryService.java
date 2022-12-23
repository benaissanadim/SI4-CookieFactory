package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.interfaces.IOrderHistory;
import org.springframework.stereotype.Component;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Component
public class OrderHistoryService implements IOrderHistory {

    public OrderRepository orderRepository;

    @Autowired
    public OrderHistoryService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrdersCancelled(Customer customer){
        return orderRepository.findByStatus(OrderStatus.CANCELED).stream()
                .sorted((o1, o2) -> o2.getStatusDateChange().compareTo(o1.getStatusDateChange()))
                .toList();
    }

    @Override
    public List<Order> lastTwoOrdersCanceled(Customer customer){
        List<Order> orders = getOrdersCancelled(customer);
        if(orders.size() < 2){
            return List.of();
        }
        return List.of(orders.get(0), orders.get(1));
    }


}
