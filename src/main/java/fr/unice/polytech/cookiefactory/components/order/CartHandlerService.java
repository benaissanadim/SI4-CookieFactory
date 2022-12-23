package fr.unice.polytech.cookiefactory.components.order;

import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.entities.order.Order;
import fr.unice.polytech.cookiefactory.exceptions.EmptyCartException;
import fr.unice.polytech.cookiefactory.interfaces.CartContentSearch;
import fr.unice.polytech.cookiefactory.interfaces.CartModifier;
import fr.unice.polytech.cookiefactory.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CartHandlerService implements CartModifier, CartContentSearch {

    CustomerRepository customerRepository;

    @Autowired
    public CartHandlerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addItemToCart(Customer customer, Cookie cookie, int quantity) {
        Item item = new Item(quantity, cookie);
        Optional<Item> optItemCart = getItemCookie(customer,item.getCookie());
        optItemCart.ifPresentOrElse(itemCart -> incrementQuantity(itemCart, item.getQuantity())
                ,()-> customer.getCart().add(item));
    }

    @Override
    public void removeFromCart(Customer customer, Item item) {
        Optional<Item> optItem = getItemCookie(customer, item.getCookie());
        optItem.ifPresentOrElse(itemCart -> decreaseQuantity(itemCart, item.getQuantity())
                , () -> customer.getCart().remove(item));
    }

    @Override
    public Optional<Item> getItemCookie(Customer customer, Cookie cookie){
        for(Item item : customer.getCart()){
            if(item.getCookie().getName().equals(cookie.getName())){
                return  Optional.of(item);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Item> getContents(Customer customer){
        return customer.getCart();
    }

    @Override
    public Order validate(Customer customer) throws EmptyCartException {
        if (getContents(customer).isEmpty())
            throw new EmptyCartException(customer.getName());
        Order newOrder = new Order();
        newOrder.setItems(getContents(customer));
        customer.setCart(new ArrayList<>());
        customerRepository.save(customer,customer.getId());
        newOrder.setCustomer(customer);
        return newOrder;
    }

    private void incrementQuantity(Item item, int quantity){
        int itemQuantity = item.getQuantity();
        item.setQuantity(itemQuantity+ quantity);
    }

    private void decreaseQuantity(Item item, int quantity) {
        int itemQuantity = item.getQuantity();
        item.setQuantity(itemQuantity - quantity);
    }
}
