package fr.unice.polytech.cookiefactory.entities.order;

import fr.unice.polytech.cookiefactory.entities.store.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialOrder extends Order{
    Event event;
}
