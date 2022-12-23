package fr.unice.polytech.cookiefactory.components.cookie;

import fr.unice.polytech.cookiefactory.interfaces.CatalogExplorator;
import fr.unice.polytech.cookiefactory.interfaces.CatalogUpdater;
import fr.unice.polytech.cookiefactory.repositories.CookieRepository;
import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogCookieService implements CatalogExplorator, CatalogUpdater {

    CookieRepository cookieRepository;

    @Autowired
    public CatalogCookieService(CookieRepository cookieRepository){
        this.cookieRepository = cookieRepository;
    }

    @Override
    public List<Cookie> listPreMadeRecipes() {
        return cookieRepository.findAll().stream()
                .filter(Cookie::isValidated).toList();
    }

    @Override
    public List<Cookie> exploreCatalog(String regexp) {
        return cookieRepository.findAll().stream()
                .filter(Cookie::isValidated)
                .filter(cookie -> cookie.getName().matches(regexp)).toList();
    }

    @Override
    public void suggestCookieInCatalog(Cookie cookie){
        cookie.setValidated(false);
        cookieRepository.save(cookie, cookie.getId());
    }

    @Override
    public void validateCookiesInCatalog(double priceThreshold) {
        cookieRepository.findAll().stream().filter(cookie -> !cookie.isValidated())
                .forEach(cookie -> {
                    if(cookie.getPrice() < priceThreshold){
                        cookie.setValidated(true);
                        cookieRepository.save(cookie, cookie.getId());
                    }else{
                        cookieRepository.deleteById(cookie.getId());
                    }
                });

    }

}