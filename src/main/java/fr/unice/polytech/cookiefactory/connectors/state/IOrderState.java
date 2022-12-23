package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import lombok.Getter;

@Getter
public abstract class IOrderState {

        protected OrderStatus status ;
        public abstract void next(OrderState pkg);

}

