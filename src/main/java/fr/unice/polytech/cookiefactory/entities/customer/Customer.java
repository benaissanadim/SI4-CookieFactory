package fr.unice.polytech.cookiefactory.entities.customer;

import fr.unice.polytech.cookiefactory.entities.order.Item;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    protected UUID id;
    protected String name;
    protected String password;
    protected CreditCard creditCard;
    protected String email;
    protected List<Item> cart;


    public Customer(String n, CreditCard c) {
        this.name = n;
        this.creditCard = c;
        this.id = UUID.randomUUID();
        cart = new ArrayList<>();
    }

}
