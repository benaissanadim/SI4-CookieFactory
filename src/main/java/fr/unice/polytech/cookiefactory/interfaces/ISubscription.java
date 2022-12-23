package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.exceptions.SubscribeException;

public interface ISubscription {

    void subscribe(String email) throws SubscribeException;
}
