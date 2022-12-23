package fr.unice.polytech.cookiefactory.repositories;

import fr.unice.polytech.cookiefactory.entities.store.cook.Cook;
import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.repositories.BasicRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CookRepository extends BasicRepositoryImpl<Cook, UUID> {

    public List<Cook> getCooksByStore(Store store ){
        return store.getCooks();
    }
}
