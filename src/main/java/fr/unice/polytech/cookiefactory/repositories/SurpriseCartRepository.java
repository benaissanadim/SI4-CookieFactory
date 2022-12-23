package fr.unice.polytech.cookiefactory.repositories;

import fr.unice.polytech.cookiefactory.entities.order.SurpriseCart;
import fr.unice.polytech.repositories.BasicRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class SurpriseCartRepository extends BasicRepositoryImpl<SurpriseCart, UUID> {
}
