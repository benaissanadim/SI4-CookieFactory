package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.interfaces.CancelOrderExecutor;
import fr.unice.polytech.cookiefactory.interfaces.IBank;
import fr.unice.polytech.cookiefactory.interfaces.IStockCancelor;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class CancelOrderManager implements CancelOrderExecutor {

    IStockCancelor stockCanceler;
    OrderRepository orderRepository;
    IBank bank;

    @Autowired
    public CancelOrderManager(IStockCancelor stockCanceler, IBank bank, OrderRepository orderRepository) {
        this.stockCanceler = stockCanceler;
        this.bank = bank;
        this.orderRepository = orderRepository;
    }

    @Override
    public void cancelOrder(Customer customer, Order order) throws StockException, InvalidPaymentException {
        if(orderRepository.findById(order.getId()).isPresent()){
            if (order.getOrderState().getStatus() == OrderStatus.PLACED) {
                order.getOrderState().cancel();
                order.setStatusDateChange(LocalDateTime.now());
                stockCanceler.cancelReservation(order.getStore(), order);
                bank.refund(customer.getCreditCard(), order.getPrice());
            }
        }

    }
}

