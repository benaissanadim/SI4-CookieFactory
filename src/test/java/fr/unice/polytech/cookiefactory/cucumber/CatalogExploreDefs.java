package fr.unice.polytech.cookiefactory.cucumber;

import fr.unice.polytech.cookiefactory.components.cookie.CatalogCookieService;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.repositories.CookieRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CatalogExploreDefs {

    @Autowired
    private CookieRepository cookieRepository;
    @Autowired
    private CatalogCookieService catalog;

    private int nb;
    private List<Cookie> cookies;


    @Given("catalog with cookies validated {string} {string} {string} {string} {string}")
    public void catalogWithCookie(
            String arg0, String arg1, String arg2, String arg3, String arg4) {

        Cookie cookie0 = mock(Cookie.class);
        Cookie cookie1 = mock(Cookie.class);
        Cookie cookie2 = mock(Cookie.class);
        Cookie cookie3 = mock(Cookie.class);
        Cookie cookie4 = mock(Cookie.class);

        when(cookie0.getName()).thenReturn(arg0);
        when(cookie1.getName()).thenReturn(arg1);
        when(cookie2.getName()).thenReturn(arg2);
        when(cookie3.getName()).thenReturn(arg3);
        when(cookie4.getName()).thenReturn(arg4);

        when(cookie0.isValidated()).thenReturn(true);
        when(cookie1.isValidated()).thenReturn(true);
        when(cookie2.isValidated()).thenReturn(true);
        when(cookie3.isValidated()).thenReturn(true);
        when(cookie4.isValidated()).thenReturn(true);

        cookieRepository.deleteAll();
        cookieRepository.save(cookie0, UUID.randomUUID());
        cookieRepository.save(cookie1, UUID.randomUUID());
        cookieRepository.save(cookie2, UUID.randomUUID());
        cookieRepository.save(cookie3, UUID.randomUUID());
        cookieRepository.save(cookie4, UUID.randomUUID());

    }

    @When("user wants to explore all cookies types")
    public void userWantsToExploreAllCookiesTypes() {
        nb = catalog.listPreMadeRecipes().size();
    }

    @Then("he finds {int} types") public void heFindsTypes(int arg0) {
        Assertions.assertEquals(arg0, nb);
    }

    @When("user wants to search a cookie and writes {string}")
    public void userWantsToSearchACookieAndWrites(String arg0) {
        cookies = catalog.exploreCatalog(arg0);

    }

    @Then("he finds {string} {string} {string}")
    public void heFinds(String arg0, String arg1, String arg2) {
        Assertions.assertEquals(arg0, cookies.get(0).getName());
        Assertions.assertEquals(arg1, cookies.get(1).getName());
        Assertions.assertEquals(arg2, cookies.get(2).getName());

    }

}
