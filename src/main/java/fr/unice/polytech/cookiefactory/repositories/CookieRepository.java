package fr.unice.polytech.cookiefactory.repositories;

import fr.unice.polytech.cookiefactory.entities.cookie.simple.Cookie;
import fr.unice.polytech.repositories.BasicRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CookieRepository extends BasicRepositoryImpl<Cookie, UUID> {



}