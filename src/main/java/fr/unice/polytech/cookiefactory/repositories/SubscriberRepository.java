package fr.unice.polytech.cookiefactory.repositories;

import fr.unice.polytech.cookiefactory.entities.customer.Subscriber;
import fr.unice.polytech.repositories.BasicRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SubscriberRepository  extends BasicRepositoryImpl<Subscriber, UUID> {
    public Boolean findByEmail(String email){
        return findAll().stream().anyMatch(customer -> customer.getEmail().equals(email));
    }
}
