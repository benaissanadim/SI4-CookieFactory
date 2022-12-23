package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.exceptions.JoinLoyaltyProgramException;

public interface LoyaltyProgramBenefits {

    boolean eligibleToDiscount(Customer customer);

    public void joinLoyaltyProgram(Customer customer) throws JoinLoyaltyProgramException;
}
