package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.connectors.decorator.EmailDecorator;
import fr.unice.polytech.cookiefactory.connectors.decorator.Notification;
import fr.unice.polytech.cookiefactory.connectors.decorator.Notifier;
import fr.unice.polytech.cookiefactory.entities.customer.Subscriber;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.SubscribeException;
import fr.unice.polytech.cookiefactory.interfaces.ISubscription;
import fr.unice.polytech.cookiefactory.interfaces.ISurpriseCartCreator;
import fr.unice.polytech.cookiefactory.repositories.OrderRepository;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;
import fr.unice.polytech.cookiefactory.repositories.SubscriberRepository;
import fr.unice.polytech.cookiefactory.repositories.SurpriseCartRepository;
import fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass.ReminderSurpriseCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class TooGoodToGoService implements ISubscription, ISurpriseCartCreator {
    private static final String REGEX_PATTERN_EMAIL = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";


    private final SubscriberRepository subscriberRepository;
    private final OrderRepository orderRepository;
    private final SurpriseCartRepository surpriseCartRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public TooGoodToGoService(SubscriberRepository subscriberRepository,
            OrderRepository orderRepository, StoreRepository storeRepository,
            SurpriseCartRepository surpriseCartRepository){
        this.subscriberRepository = subscriberRepository;
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.surpriseCartRepository = surpriseCartRepository;

    }

    @Override public void subscribe(String email) throws SubscribeException {
        Pattern validEmailPattern = Pattern.compile(REGEX_PATTERN_EMAIL, Pattern.CASE_INSENSITIVE);
        if(!validEmailPattern.matcher(email).find()){
            throw new SubscribeException("invalid email!");
        }
        if(subscriberRepository.findByEmail(email)){
            throw new SubscribeException("email already used!");
        }
        Subscriber subscriber = new Subscriber(email);
        subscriberRepository.save(subscriber, subscriber.getId());
    }


    /**
     * Launch the suprise cart task for each store
     */
    public void launchSurpriseCartTask() {
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            ReminderSurpriseCart surpriseCartReminder = new ReminderSurpriseCart(store,
                    orderRepository, surpriseCartRepository);
            try {
                surpriseCartReminder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int sendMessage(Store store, double price) {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        Notification notifyStack = new Notifier();
        notifyStack = new EmailDecorator(notifyStack);
        int nb=0;

        for (Subscriber s : subscribers) {
            nb++;
            String email = s.getEmail();
            String subject = "Too Good To Go surprise cart !";
            String stringBuilder =
                    "Hey subscriber ! \n " + "A new surprise cart is available in store " + store.getName()
                            + " located in " + store.getAddress() + "\n Only for " + price + "$"
                            + "\n We are waiting for you!";
            notifyStack.sendMessage(email, subject, stringBuilder);
        }
        return nb;
    }

    public StoreRepository getStoreRepository() {
        return storeRepository;
    }
}
