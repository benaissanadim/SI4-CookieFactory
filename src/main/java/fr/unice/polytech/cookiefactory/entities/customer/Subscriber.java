package fr.unice.polytech.cookiefactory.entities.customer;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Subscriber {
    private String email;
    private UUID id;

    public Subscriber(String email){
        this.email = email;
        this.id = UUID.randomUUID();
    }
}
