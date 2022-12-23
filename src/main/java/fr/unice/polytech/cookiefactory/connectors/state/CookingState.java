package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;

public class CookingState extends IOrderState {

    public CookingState(){
        status = OrderStatus.COOKING;
    }

    public void next(OrderState pkg) {
        pkg.setState(new ReadyState());
        pkg.setStatus(pkg.getState().getStatus());
    }
}
