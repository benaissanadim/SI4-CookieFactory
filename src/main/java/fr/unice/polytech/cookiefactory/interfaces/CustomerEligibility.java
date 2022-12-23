package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;

import java.time.LocalDateTime;

public interface CustomerEligibility {
     boolean eligibleToOrder(Customer customer, LocalDateTime date);
     boolean eligibleToDiscount(Customer customer);

}
