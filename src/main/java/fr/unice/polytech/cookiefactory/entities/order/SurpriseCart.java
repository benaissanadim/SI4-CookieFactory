package fr.unice.polytech.cookiefactory.entities.order;

import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SurpriseCart {

    private List<Item> surpriseCookies;
    private Store store;
    private double price;
    private UUID id;

    public SurpriseCart(Store store){
        this.id = UUID.randomUUID();
        this.store = store;
    }

    public SurpriseCart(){
        this.id = UUID.randomUUID();
    }
}
