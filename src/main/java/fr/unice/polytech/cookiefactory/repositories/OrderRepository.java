package fr.unice.polytech.cookiefactory.repositories;

import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.repositories.BasicRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepository extends BasicRepositoryImpl<Order, UUID> {


    public List<Order> findByStatus(OrderStatus status) {
        return findAll().stream().filter(order -> order.getOrderState().getStatus().
                equals(status)).toList();
    }

}
