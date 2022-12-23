package fr.unice.polytech.cookiefactory.entities.customer;

import fr.unice.polytech.cookiefactory.entities.order.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class LoyalCustomer extends Customer{

    private int nbCookieOrdered;

    public LoyalCustomer(UUID id, String name,  String email, String password, CreditCard creditCard, List<Item> cart) {
        super(name, creditCard);
        this.id = id;
        this.email = email;
        this.nbCookieOrdered = 0;
    }
}
