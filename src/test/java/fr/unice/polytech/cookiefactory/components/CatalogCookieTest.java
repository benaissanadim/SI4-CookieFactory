package fr.unice.polytech.cookiefactory.components;

import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.cookiefactory.interfaces.CatalogExplorator;
import fr.unice.polytech.cookiefactory.interfaces.CatalogUpdater;
import fr.unice.polytech.cookiefactory.repositories.CookieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class CatalogCookieTest {
    Cookie cookie1 ;
    Cookie cookie2 ;

    @Autowired CookieRepository cookieRepository ;

    @Autowired CatalogExplorator catalogExplorator ;

    @Autowired CatalogUpdater catalogUpdater ;

    @BeforeEach
    public void setUp(){
        cookie1 = new Cookie();
        cookie1.setId(new UUID(0,0));
        cookie1.setValidated(true);
        cookie2 = new Cookie();
    }

    @Test
    public void listPreMadeRecipesJustCookie1IsValidated(){
        cookieRepository.save(cookie1 , new UUID(0,0));
        List<Cookie> cookies = catalogExplorator.listPreMadeRecipes();
        Assertions.assertEquals(cookie1,cookies.get(0));
        Assertions.assertEquals(1,cookies.size());
    }

    @Test
    public void exploreCatalogueTofindCookie1(){
        cookieRepository.save(cookie1 , new UUID(0,0));
        cookie1.setName("Chocolat");
        List<Cookie> cookies = catalogExplorator.exploreCatalog("Chocolat");
        Assertions.assertEquals(1,cookies.size());
    }

    @Test
    public void exploreCatalogueNoCookieValidated(){
        cookie1.setValidated(false);
        Assertions.assertNotNull( catalogExplorator.exploreCatalog("Chocolat"));
    }

    @Test
    public void suggestCookieInCatalogCaramelCookie(){
        Cookie caramel = new Cookie();
        caramel.setId(new UUID(0,0));
        catalogUpdater.suggestCookieInCatalog(caramel);
        Assertions.assertEquals(Optional.of(caramel),cookieRepository.findById(new UUID(0,0)));
    }

    @Test
    public void validateCookiesInCatalogThresholdEquals3(){
        cookie1.setPrice(2);
        cookieRepository.save(cookie1 , new UUID(0,0));
        catalogUpdater.suggestCookieInCatalog(cookie1);
        catalogUpdater.validateCookiesInCatalog(4);
        Assertions.assertEquals(Optional.of(cookie1),cookieRepository.findById(new UUID(0,0)));
    }

    @Test
    public void validateCookiesInCatalogThresholdEquals1(){
        cookie1.setPrice(2);
        catalogUpdater.suggestCookieInCatalog(cookie1);
        catalogUpdater.validateCookiesInCatalog(1);
        Assertions.assertEquals(Optional.empty(),cookieRepository.findById(new UUID(0,0)));
    }

}
