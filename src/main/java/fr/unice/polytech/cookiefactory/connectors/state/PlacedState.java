package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlacedState extends IOrderState {

    public PlacedState(){
        status = OrderStatus.PLACED;
    }

    @Override
    public void next(OrderState pkg) {
        pkg.setState(new CookingState());
        pkg.setStatus(pkg.getState().getStatus());
        log.info("info");
        log.warn("");

    }

}
