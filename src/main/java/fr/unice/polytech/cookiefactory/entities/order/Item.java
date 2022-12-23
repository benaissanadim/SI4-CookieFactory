package fr.unice.polytech.cookiefactory.entities.order;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * a class our order item
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private int quantity;
    private Cookie cookie;
}