package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;

public class CanceledState extends IOrderState {

    public CanceledState(){
        status = OrderStatus.CANCELED;
    }

    public void next(OrderState pkg) {
        pkg.setState(new CanceledState());
    }

}
