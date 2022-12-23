package fr.unice.polytech.cookiefactory.entities.store.stocks;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
public class Stock {

    private StockIngredient availableStock;
    private StockIngredient reservedStock;

}
