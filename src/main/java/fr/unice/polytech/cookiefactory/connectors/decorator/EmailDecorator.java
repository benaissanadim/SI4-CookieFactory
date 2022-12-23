package fr.unice.polytech.cookiefactory.connectors.decorator;

import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailDecorator extends BaseDecorator{

    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);

    public EmailDecorator(Notification wrappee) {
        super(wrappee);
    }

    @Override
    public void sendMessage(String to, String subject, String text) {
        super.sendMessage(to, subject, text);
        sendEmail(to, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {

        try {
            Session session = setUpSession();
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("nadim.ben.aissaa@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeBodyPart textPart = new MimeBodyPart();

            try {
                attachmentPart.attachFile(new File("cookie.png"));
                textPart.setText(text);
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);
            } catch (IOException e) {
                e.printStackTrace();
            }

            message.setContent(multipart);

            logger.debug("sending...");
            Transport.send(message);
            logger.info("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private Session setUpSession(){
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nadim.ben.aissaa@gmail.com", "asbzcfxxmregpogv");
            }

        });
        session.setDebug(true);

        return session;

    }
}
