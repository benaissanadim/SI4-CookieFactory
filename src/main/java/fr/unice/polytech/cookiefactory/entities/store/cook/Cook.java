package fr.unice.polytech.cookiefactory.entities.store.cook;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Cook {
    private String name;
    private CookScheduler cookScheduler;
}
