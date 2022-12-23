package fr.unice.polytech.cookiefactory.exceptions;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyCartException extends Exception {

    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);

    public EmptyCartException(String name) {
        logger.info(name + " have an empty cart!");
    }
}
