package fr.unice.polytech.cookiefactory.connectors.decorator;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Notifier implements Notification{

    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);

    @Override
    public void sendMessage(String to, String subject, String text) {
        logger.info("sending notification ...");
    }
}