package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.EmptyCartException;
import fr.unice.polytech.cookiefactory.interfaces.CartModifier;
import fr.unice.polytech.cookiefactory.repositories.CustomerRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartDefs {

    private Customer customer;
    private Cookie cookie1;
    private Order order;

    @Autowired private CustomerRepository customerRepository;

    @Autowired private CartModifier cartModifier;

    @Given("I am a client {string}") public void iAmAClient(String arg0) {
        customer = new Customer();
        customer.setName(arg0);
        customerRepository.save(customer, UUID.randomUUID());
    }

    @Given("I have an empty cart") public void iHaveAnEmptyCart() {
        customer.setCart(new ArrayList<>());
    }

    @When("I choose a {string} cookie with {int} quantity")
    public void iChooseACookieWithQuantity(String arg0, int arg1) {
        cookie1 = new Cookie();
        cookie1.setName(arg0);
        cartModifier.addItemToCart(customer, cookie1, arg1);
    }

    @Then("cart contains {int} item {string} {int} quantity")
    public void cartContainsItemQuantity(int arg0, String arg1, int arg2) {
        List<Item> cart = customer.getCart();
        Assertions.assertEquals(arg0, cart.size());
        Assertions.assertEquals(arg1, cart.get(0).getCookie().getName());
        Assertions.assertEquals(arg2, cart.get(0).getQuantity());
    }

    @Given("I have a cart that already contains {int} {string} cookies")
    public void iHaveACartThatAlreadyContainsCookies(int arg0, String arg1) {
        cookie1 = new Cookie();
        cookie1.setName(arg1);
        Item item = new Item(arg0, cookie1);
        List<Item> items = new ArrayList<>();
        items.add(item);
        customer.setCart(items);
    }

    @Then("my cart contains {int} {string} & {int} {string}")
    public void myCartContains(int arg0, String arg1, int arg2, String arg3) {
        List<Item> cart = customer.getCart();
        Assertions.assertEquals(arg1, cart.get(1).getCookie().getName());
        Assertions.assertEquals(arg0, cart.get(1).getQuantity());
        Assertions.assertEquals(arg3, cart.get(0).getCookie().getName());
        Assertions.assertEquals(arg2, cart.get(0).getQuantity());
    }

    @When("I validate my cart of {int} {string}")
    public void iValidateMyCartOf(int arg0, String arg1) throws EmptyCartException {
        cookie1 = new Cookie();
        cookie1.setName(arg1);
        Item item = new Item(arg0, cookie1);
        List<Item> items = new ArrayList<>();
        items.add(item);
        customer.setCart(items);
        order = cartModifier.validate(customer);
    }

    @Then("my order cantains {int} {string}")
    public void myOrderCantains(int arg0, String arg1) {
        Item item = order.getItems().get(0);
        Assertions.assertEquals(arg0, item.getQuantity());
        Assertions.assertEquals(arg1, item.getCookie().getName());
    }

    @When("I want to add {int} more {string} cookies")
    public void iWantToAddMoreCookies(int arg0, String arg1) {
        cookie1 = new Cookie();
        cookie1.setName(arg1);
        Item item = new Item(arg0, cookie1);
        cartModifier.addItemToCart(customer, cookie1, arg0);
    }

    @Then("the quantity of {string} cookie is set to {int} in the basket")
    public void theQuantityOfCookieIsSetToInTheBasket(String arg0, int arg1) {
        Item item = customer.getCart().get(0);
        Assertions.assertEquals(arg1, item.getQuantity());
        Assertions.assertEquals(arg0, item.getCookie().getName());
    }

    @When("I want to remove {int} more {string} cookies")
    public void iWantToRemoveMoreCookies(int arg0, String arg1) {
        cookie1 = new Cookie();
        cookie1.setName(arg1);
        Item item = new Item(arg0, cookie1);
        cartModifier.removeFromCart(customer, item);
    }
}