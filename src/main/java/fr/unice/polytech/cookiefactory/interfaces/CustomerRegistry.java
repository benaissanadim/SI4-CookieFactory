package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.exceptions.PasswordValidationException;
import fr.unice.polytech.cookiefactory.exceptions.SignUpException;
import fr.unice.polytech.cookiefactory.exceptions.EmailValidationException;

public interface CustomerRegistry {

    public Customer signUp(String email, String pwd)
            throws EmailValidationException, PasswordValidationException, SignUpException;

    public Boolean signIn(String email, String pwd);
}
