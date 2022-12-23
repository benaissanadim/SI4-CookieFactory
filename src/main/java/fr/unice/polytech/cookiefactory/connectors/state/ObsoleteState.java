package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;

public class ObsoleteState extends IOrderState {

    public ObsoleteState(){
        status = OrderStatus.OBSOLETE;
    }

    @Override public void next(OrderState pkg) {
        pkg.setState(new ObsoleteState());
    }

}
