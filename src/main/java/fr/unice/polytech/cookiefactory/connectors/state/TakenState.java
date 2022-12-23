package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;

public class TakenState  extends IOrderState {

    public TakenState(){
        status = OrderStatus.TAKEN;
    }

    @Override
    public void next(OrderState pkg) {
        pkg.setState(new TakenState());
    }

}
