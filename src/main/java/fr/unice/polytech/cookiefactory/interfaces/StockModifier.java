package fr.unice.polytech.cookiefactory.interfaces;

import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Ingredient;
import fr.unice.polytech.cookiefactory.entities.store.stocks.StockIngredient;
import fr.unice.polytech.cookiefactory.exceptions.StockException;

public interface StockModifier {


    public void increase(StockIngredient stock, Ingredient ingredient, Integer quantity) throws StockException;
    public void decrease(StockIngredient stock, Ingredient ingredient, Integer quantity) throws StockException ;

    public void addToStock(StockIngredient stockInitial, StockIngredient stockToAdd) throws StockException ;
    public void decreaseFromStock(StockIngredient stockInitial, StockIngredient stockToDecrease) throws StockException ;

}
