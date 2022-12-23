package fr.unice.polytech.cookiefactory.connectors.state;

import fr.unice.polytech.cookiefactory.entities.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderState {

    private OrderStatus status ;
    private IOrderState state = new CreatedState();

    public void next() {
        state.next(this);
        status = state.getStatus();
    }

    public void cancel(){
        state = new CanceledState();
        status = state.getStatus();
    }

    public void obsolete(){
        state = new ObsoleteState();
        status = state.getStatus();
    }

}
