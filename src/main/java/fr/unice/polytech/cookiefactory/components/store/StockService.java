package fr.unice.polytech.cookiefactory.components.store;

import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Ingredient;
import fr.unice.polytech.cookiefactory.entities.store.stocks.StockIngredient;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.interfaces.StockModifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StockService implements StockModifier {

    public void increase(StockIngredient stock, Ingredient ingredient, Integer quantity) throws StockException {
        if(stock == null) throw new StockException("no StockIngredient found");
        if(quantity<0) throw new StockException("quantity to add to stock must be positif integer");
        stock.compute(ingredient, (key, val) -> val == null ? quantity : quantity + val);
    }

    public void decrease(StockIngredient stock, Ingredient ingredient, Integer quantity) throws StockException {
        if(stock == null) throw new StockException("no StockIngredient found");
        if(quantity<0) throw new StockException("quantity to add to stock must be positif integer");

        Integer nb = stock.get(ingredient);
        if(nb == null){
            throw new StockException("stock unavailable");
        }
        else if(nb < quantity) {
        }
        else {
            stock.put(ingredient,nb - quantity);
        }
    }

    public void addToStock(StockIngredient stockInitial, StockIngredient stockToAdd) throws StockException {
        for(Map.Entry<Ingredient,Integer> m : stockToAdd.entrySet()){
            increase(stockInitial, m.getKey(), m.getValue());
        }
    }

    public void decreaseFromStock(StockIngredient stockInitial, StockIngredient stockToDecrease) throws StockException {
        for(Map.Entry<Ingredient,Integer> m : stockToDecrease.entrySet()){
            decrease(stockInitial, m.getKey(), m.getValue());
        }
    }

}
