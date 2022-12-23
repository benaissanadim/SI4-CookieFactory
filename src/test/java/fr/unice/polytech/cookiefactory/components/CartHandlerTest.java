package fr.unice.polytech.cookiefactory.components;

import fr.unice.polytech.cookiefactory.entities.customer.CreditCard;
import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.entities.order.Item;
import fr.unice.polytech.cookiefactory.exceptions.EmptyCartException;
import fr.unice.polytech.cookiefactory.interfaces.CartContentSearch;
import fr.unice.polytech.cookiefactory.interfaces.CartModifier;
import fr.unice.polytech.cookiefactory.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest

public class CartHandlerTest {
    private Customer customer ;
    private Item item1 ;
    private Item item2 ;
    private Cookie cookie1 ;
    private Cookie cookie2 ;
    private List<Item> cart ;


    @Autowired CustomerRepository customerRepository;

    @Autowired CartModifier cartModifier;

    @Autowired CartContentSearch cartContentSearch;


    @BeforeEach
    public  void  setUp(){
        customer = new Customer("Hamza",new CreditCard());
        cookie1 = new Cookie();
        cookie1.setName("Chocolala");
        item1 = new Item(2 , cookie1 );
        cookie2 = new Cookie();
        item2 = new Item(3,cookie2);
        cart = new ArrayList<>();
        cart.add(item1);
        cart.add(item2);
        customer.setCart(cart);
        customer.setId(new UUID(0,0));
        cookie2.setName("Vanille");

    }

    @Test
    public void getItemCookieChocolala(){
        Cookie cookie = new Cookie();
        cookie.setName("Chocolala");
        Optional<Item> chocolala = cartContentSearch.getItemCookie(customer , cookie);
        Assertions.assertEquals(Optional.of(item1) , chocolala);
    }

    @Test
    public void getItemCartEmpty(){
        Cookie cookie = new Cookie();
        cookie.setName("Chocolala");
        customer.setCart(new ArrayList<>());
        Optional<Item> empty = cartContentSearch.getItemCookie(customer , cookie);
        Assertions.assertEquals(Optional.empty() , empty);
    }

    @Test
    public void addItemToCartTest(){
        Cookie caramel = new Cookie();
        caramel.setName("Caramel");
        Item item3 = new Item(2,caramel);
        cartModifier.addItemToCart(customer,caramel,2);
        Assertions.assertEquals(3,customer.getCart().size());
    }

    @Test
    public void addItemToCartCookieExist(){
        Cookie chocolat = new Cookie();
        chocolat.setName("Chocolala");
        Item item3 = new Item(2,chocolat);
        cartModifier.addItemToCart(customer,chocolat, 2);
        Assertions.assertEquals(2,customer.getCart().size());
        Assertions.assertEquals(4,customer.getCart().get(0).getQuantity());
    }

    @Test
    public void removeFromCartChocolatCookie(){
        cartModifier.removeFromCart(customer,item1);
        Assertions.assertEquals(0,customer.getCart().get(0).getQuantity());
    }

    @Test
    public void getContentsTestReturnCart(){
        Assertions.assertEquals(cart,cartContentSearch.getContents(customer));
    }

    @Test
    public void validateTest() throws EmptyCartException {
        cartModifier.validate(customer);
        Assertions.assertEquals(true,customerRepository.existsById(new UUID(0,0)));
    }

    @Test
    public void validateCustomerCartISEmpty() throws EmptyCartException {
        Customer customer1 = new Customer();
        List<Item> carts = new ArrayList<>();
        customer1.setCart(carts);
        Assertions.assertThrows(EmptyCartException.class ,() -> cartModifier.validate(customer1));
    }

    @Test
    public void removeFromCartSuccessfully(){
        Customer customer1 = new Customer();
        List<Item> carts = new ArrayList<>();
        carts.add(new Item(3,new Cookie()));
        customer1.setCart(carts);
        carts.get(0).getCookie().setName("cookie");
        Cookie cookie = new Cookie();
        cookie.setName("cookie");
        cartModifier.removeFromCart(customer1,new Item(3,cookie));
        Assertions.assertEquals(0,carts.get(0).getQuantity());
    }







}
