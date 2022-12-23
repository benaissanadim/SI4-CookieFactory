package fr.unice.polytech.cookiefactory.repositories;

import fr.unice.polytech.cookiefactory.entities.store.Store;
import fr.unice.polytech.repositories.BasicRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class StoreRepository extends BasicRepositoryImpl<Store, UUID> {
    
}
