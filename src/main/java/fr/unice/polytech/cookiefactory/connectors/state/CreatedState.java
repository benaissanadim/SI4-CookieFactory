package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;

public class CreatedState extends IOrderState {

    public CreatedState(){
        status = OrderStatus.CREATED;
    }

    @Override public void next(OrderState pkg) {
        pkg.setState(new PlacedState());
        pkg.setStatus(pkg.getState().getStatus());
    }
}
