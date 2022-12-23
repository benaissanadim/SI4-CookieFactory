package fr.unice.polytech.cookiefactory.connectors.decorator;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSDecorator extends BaseDecorator {

    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);

    public SMSDecorator(Notification wrappee) {
        super(wrappee);
    }

    @Override
    public void sendMessage(String to, String subject, String text) {
        super.sendMessage(to, subject, text);
        sendSMS(to, subject, text);
    }

    private void sendSMS(String to, String subject, String text) {
        logger.info("send in sms ... " +
                "\nto telephone number :" + to +
                "\nsubject"+ subject+
                "\nmsg :" + text);
    }
}
