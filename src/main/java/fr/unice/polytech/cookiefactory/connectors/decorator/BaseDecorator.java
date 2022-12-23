package fr.unice.polytech.cookiefactory.connectors.decorator;

public abstract class BaseDecorator implements Notification {
    private Notification wrappee;

    public BaseDecorator(Notification wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void sendMessage(String to, String subject, String text) {
        wrappee.sendMessage(to,subject,text);
    }
}
