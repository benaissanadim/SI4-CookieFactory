package fr.unice.polytech.cookiefactory.connectors.proxy;

import fr.unice.polytech.cookiefactory.entities.customer.CreditCard;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;
import fr.unice.polytech.cookiefactory.interfaces.IBank;

public class Bank implements IBank {


    @Override
    public void pay(CreditCard creditCard, double price) throws InvalidPaymentException {
        double amount = creditCard.getAmount();
        //if (amount < price) {
        //    throw new InvalidPaymentException("not enough amount in your credit card");
        //} else {
        amount-=price;
        creditCard.setAmount(amount);

    }

    @Override
    public void refund(CreditCard creditCard, double price) {
        double amount = creditCard.getAmount();
        amount+=price;
        creditCard.setAmount(amount);
    }
}
