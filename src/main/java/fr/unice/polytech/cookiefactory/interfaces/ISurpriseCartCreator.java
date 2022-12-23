package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.store.Store;

public interface ISurpriseCartCreator {

    void launchSurpriseCartTask();
    int sendMessage(Store store, double price);
}
