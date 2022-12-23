package fr.unice.polytech.cookiefactory.components.store;

import fr.unice.polytech.cookiefactory.entities.cookie.party.PartyCookie;
import fr.unice.polytech.cookiefactory.aspects.ComponentLogger;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.*;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Flavour;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Ingredient;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.IngredientDose;
import fr.unice.polytech.cookiefactory.entities.cookie.ingredient.Topping;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.entities.store.stocks.Stock;
import fr.unice.polytech.cookiefactory.entities.store.stocks.StockIngredient;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.StockException;
import fr.unice.polytech.cookiefactory.interfaces.IStockReservation;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;

import fr.unice.polytech.cookiefactory.interfaces.IStockCancelor;
import fr.unice.polytech.cookiefactory.interfaces.IStockPreparation;
import fr.unice.polytech.cookiefactory.interfaces.StockModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StockManagerService implements IStockReservation, IStockCancelor, IStockPreparation {

    StockModifier stockModifier;
    StoreRepository storeRepository;
    Logger logger = LoggerFactory.getLogger(ComponentLogger.class);

    @Autowired
    public StockManagerService(StockModifier stockModifier, StoreRepository storeRepository) {
        this.stockModifier = stockModifier;
        this.storeRepository = storeRepository;
    }

    @Override
    public void prepare(Store store , Order order) throws StockException {
         Stock stock = store.getStock();
         StockIngredient requiredStock = requiredStock(order);
         store.setStock(stock);
         logger.info(store.getStock().toString());
         stockModifier.decreaseFromStock(stock.getAvailableStock(), requiredStock);
         storeRepository.save(store, store.getId());
    }

    @Override
    public boolean isEnough(Store store ,Order order) throws StockException {
        Stock stock = store.getStock();
        StockIngredient stockRequired = requiredStock(order);
        try {
            for (Ingredient ing : stockRequired.keySet()) {
                if (stockRequired.get(ing) > stock.getAvailableStock().get(ing)) {

                    return false;
                }
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }

    }

    @Override
    public void reserve(Store store, Order order) throws StockException {
        Stock stock = store.getStock();
        increaseDecreaseStock(order, stock.getAvailableStock(), stock.getReservedStock());
        store.setStock(stock);
        storeRepository.save(store, store.getId());
    }

    public void cancelReservation(Store store, Order order) throws StockException {
        Stock stock = store.getStock();
        increaseDecreaseStock(order, stock.getReservedStock(), stock.getAvailableStock());
        store.setStock(stock);
        storeRepository.save(store, store.getId());
    }

    private void increaseDecreaseStock(Order order, StockIngredient stockToDecrease, StockIngredient stockToIncrease) throws StockException {
        StockIngredient requiredStock = requiredStock(order);
        stockModifier.decreaseFromStock(stockToDecrease, requiredStock);
        stockModifier.addToStock(stockToIncrease, requiredStock);
    }

    private StockIngredient requiredStock(Order order) throws StockException {

        StockIngredient stockAndQuantity = new StockIngredient();
        for (Item orderItem : order.getItems()) {
            List<Ingredient> ingredients = new ArrayList<>();
            Cookie cookie = orderItem.getCookie();
            if(cookie.getType().equals(CookieType.SIMPLE)){
                SimpleCookie simpleCookie = (SimpleCookie) cookie;
                ingredients.add(simpleCookie.getDough());
                Flavour flavour = simpleCookie.getFlavour();
                if (flavour != null) {
                    ingredients.add(flavour);
                }
                List<Topping> toppings = simpleCookie.getToppings();
                if(toppings != null){
                    ingredients.addAll(toppings);
                }
            }
            else{
                PartyCookie personnalizedCookie = (PartyCookie) cookie;
                for(IngredientDose ingredientDose :personnalizedCookie.getIngredients()){
                    for(int i =0 ; i < ingredientDose.getDose() ; i++)
                        ingredients.add(ingredientDose.getIngredient());
                }
            }
            for (Ingredient ingredient : ingredients) {
                stockModifier.increase(stockAndQuantity, ingredient, orderItem.getQuantity());
            }

        }
        return stockAndQuantity;
    }

}
