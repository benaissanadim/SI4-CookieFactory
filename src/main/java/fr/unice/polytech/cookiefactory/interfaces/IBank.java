package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.CreditCard;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;

public interface IBank {

    void pay(CreditCard creditCard, double price) throws InvalidPaymentException;
    void refund(CreditCard creditCard, double price) throws InvalidPaymentException;
}
