package fr.unice.polytech.cookiefactory.exceptions;

/**
 * JoinLoyaltyProgramException thrown when
 * an account is not eligible for the program
 */
public class JoinLoyaltyProgramException extends Exception {

    public JoinLoyaltyProgramException(String message){
        super(message);
    }

}
