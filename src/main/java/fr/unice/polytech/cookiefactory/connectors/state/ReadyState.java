package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;

public class ReadyState extends IOrderState {

    public ReadyState(){
        status = OrderStatus.READY;
    }
    @Override
    public void next(OrderState pkg) {
        pkg.setState(new TakenState());
        pkg.setStatus(pkg.getState().getStatus());

    }
}
