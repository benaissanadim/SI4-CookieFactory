package fr.unice.polytech.cookiefactory.entities.store;

import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.stocks.Stock;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * * class that represents a store
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"name"})
@NoArgsConstructor
public class Store {
    protected UUID id;
    protected String name;
    protected String address;
    protected Double taxPercentage;
    protected List<Order> orders ;
    protected List<Cook> cooks ;
    protected DayTimeTable timeTable;
    protected Stock stock;

    public Store(String name, String address, Double taxPercentage) {
        stock = new Stock();
        this.name = name;
        this.address = address;
        this.taxPercentage = taxPercentage;
        timeTable = new DayTimeTable();
        orders = new ArrayList<>();
        cooks =  new ArrayList<>();
    }

}
