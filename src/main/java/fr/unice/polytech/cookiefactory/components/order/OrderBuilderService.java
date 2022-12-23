package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.components.store.CookManager;
import fr.unice.polytech.cookiefactory.connectors.proxy.BankProxy;
import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.order.SpecialOrder;
import fr.unice.polytech.cookiefactory.entities.store.SpecialStore;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;
import fr.unice.polytech.cookiefactory.interfaces.*;
import fr.unice.polytech.cookiefactory.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class OrderBuilderService implements OrderPlaceExecutor, IChooseStoreTime{


    OrderRepository orderRepository;
    CookManager cookManager;
    CustomerManager customerManager;
    OrderPriceCalculator priceCalculator;
    IStockReservation stockReservation;
    StoreRepository storeRepository;
    IStoreAvailable storeAvailable;
    BankProxy bank;

    @Autowired
    public OrderBuilderService(OrderRepository orderRepository, CookManager cookManager,
            CustomerManager customerManager, OrderPriceCalculator priceCalculator, IStockReservation stockReservation,
            StoreRepository storeRepository, IStoreAvailable storeAvailable, BankProxy bank) {
        this.orderRepository = orderRepository;
        this.cookManager = cookManager;
        this.customerManager = customerManager;
        this.priceCalculator = priceCalculator;
        this.stockReservation = stockReservation;
        this.storeRepository = storeRepository;
        this.storeAvailable = storeAvailable;
        this.bank = bank;
    }

    public void chooseStore(Order order, Store store) throws StoreNotFoundException {
        Optional<Store> optStore = storeRepository.findById(store.getId());
        if (optStore.isEmpty()) {
            throw new StoreNotFoundException("store not found in our factory");
        }
        if((order instanceof SpecialOrder specialOrder) && (store instanceof SpecialStore specialStore)){
            if(!specialStore.getEvents().contains(specialOrder.getEvent())){
                throw new StoreNotFoundException("store does not contain the event");
            }
        }
        order.setStore(store);
    }

    public void choosePickUpTime(Order order, LocalDateTime dateTime)
            throws StoreNotFoundException, StoreClosedException {

        Store store = order.getStore();
        if(store == null){
            throw new StoreNotFoundException("please add the store to pick order before choosing time !");
        }
        if(!storeAvailable.isAvailable(store, dateTime)){
            throw new StoreClosedException("shop is closed ! Please choose another time or Date");
        }
        order.setPickUpDateTime(dateTime);
    }


    public void validateOrder(Customer customer, Order order)
            throws NoCookAvailableException, InabilityToOrderException, StockException,
            InvalidPaymentException {


        //OrderProcess.execute

        order.setPrice(priceCalculator.calculatePriceFinal(order));

        Optional<Cook> optCook = cookManager.findCook(order);
        if(optCook.isEmpty()){
            throw new NoCookAvailableException("no cook available");
        }
        Store store = order.getStore();
        if(!customerManager.eligibleToOrder(order.getCustomer(), LocalDateTime.now()))
            throw new InabilityToOrderException("You can't order at this time");
        if (!stockReservation.isEnough(store, order))
            throw new StockException("not enough stock");
        bank.pay(customer.getCreditCard(), order.getPrice());
        cookManager.affectCookToOrder(optCook.get(), order);
        order.getOrderState().next();
        stockReservation.reserve(store, order);
        orderRepository.save(order, order.getId());
    }

}

