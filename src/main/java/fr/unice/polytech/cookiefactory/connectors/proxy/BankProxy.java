package fr.unice.polytech.cookiefactory.connectors.proxy;

import fr.unice.polytech.cookiefactory.entities.customer.CreditCard;
import fr.unice.polytech.cookiefactory.exceptions.InvalidPaymentException;
import fr.unice.polytech.cookiefactory.interfaces.IBank;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BankProxy implements IBank {

    private Bank realbank = new Bank();

    public void pay(CreditCard creditCard, double price) throws InvalidPaymentException {

        double amount = creditCard.getAmount();
        if (amount < price) {
            throw new InvalidPaymentException("not enough amount in your credit card");
        }
        if(creditCard.getExpirationDate().isBefore(LocalDate.now())){
            throw new InvalidPaymentException("Your card is Expirated");
        }
        realbank.pay(creditCard,price);
    }

    public void refund(CreditCard creditCard, double price) throws InvalidPaymentException {
        if(creditCard.getExpirationDate().isBefore(LocalDate.now())) {
            throw new InvalidPaymentException("Your card is Expirated");
        }
        realbank.refund(creditCard,price);
    }


}
