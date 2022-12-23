package fr.unice.polytech.cookiefactory.entities.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

/**
 * a class that represents a credit card
 */
@Getter
@Setter
@NoArgsConstructor
public class CreditCard {

    private LocalDate expirationDate;
    private double amount;

}

