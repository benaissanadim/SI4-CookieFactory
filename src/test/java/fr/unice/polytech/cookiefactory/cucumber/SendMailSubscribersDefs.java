package fr.unice.polytech.cookiefactory.cucumber;


import fr.unice.polytech.cookiefactory.components.order.TooGoodToGoService;
import fr.unice.polytech.cookiefactory.entities.order.SurpriseCart;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.cookiefactory.exceptions.SubscribeException;
import fr.unice.polytech.cookiefactory.repositories.StoreRepository;
import fr.unice.polytech.cookiefactory.repositories.SubscriberRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SendMailSubscribersDefs {

    private Store store;
    private SurpriseCart surpriseCart;

    @Autowired
    StoreRepository storeRepository;

    @Autowired SubscriberRepository subscriberRepository;

    @Autowired TooGoodToGoService tooGoodToGoService;

    @Given("subscribers {string} {string} {string}")
    public void subscribers(String arg0, String arg1, String arg2) throws SubscribeException {
        subscriberRepository.deleteAll();
        tooGoodToGoService.subscribe(arg0);
        tooGoodToGoService.subscribe(arg1);
        tooGoodToGoService.subscribe(arg2);
    }

    @And("store {string} address {string}")
    public void storeAddress(String arg0, String arg1) {
        store = new Store();
        store.setName(arg0);
        store.setAddress(arg1);
        store.setId(UUID.randomUUID());
        storeRepository.save(store, store.getId());
    }

    @When("a user add his email {string}")
    public void aUserAddHisEmail(String arg0)
            throws SubscribeException {
        tooGoodToGoService.subscribe(arg0);
    }

    @Then("his email {string} is added successfully")
    public void hisEmailIsAddedSuccessfully(String arg0) {
        Assertions.assertTrue(subscriberRepository.findByEmail(arg0));
    }

    @And("user cant add again his email {string}")
    public void userCantAddAgainHisEmail(String arg0) {
        assertThrows(SubscribeException.class, ()-> tooGoodToGoService.subscribe(arg0));
    }

    @When("a new order surprise is created with price {int}$ in store {string}")
    public void aNewOrderSurpriseIsCreatedWithPrice$InStore(int arg0, String arg1) {
        store.setName(arg1);
        store.setAddress("adress");
        surpriseCart = new SurpriseCart(store);
        surpriseCart.setPrice(arg0);
    }

    @Then("factory emails all {int} subscribers")
    public void factoryEmailsAllSubscribers(int arg0) {
        assertEquals(arg0,tooGoodToGoService.sendMessage(surpriseCart.getStore(),
                surpriseCart.getPrice()));
    }
}
