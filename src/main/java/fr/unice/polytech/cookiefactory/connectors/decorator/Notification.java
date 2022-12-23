package fr.unice.polytech.cookiefactory.connectors.decorator;

public interface Notification {
    public void sendMessage(String to, String subject, String text);
}