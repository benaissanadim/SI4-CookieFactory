package fr.unice.polytech.cookiefactory.repositories;


import fr.unice.polytech.cookiefactory.entities.customer.Customer;
import fr.unice.polytech.repositories.BasicRepositoryImpl;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CustomerRepository extends BasicRepositoryImpl<Customer, UUID> {

    public Boolean findByEmail(String email){
        return findAll().stream().anyMatch(customer -> customer.getEmail().equals(email));
    }

    public Boolean findByEmailPwd(String email, String pwd){
        return findAll().stream().anyMatch(customer -> customer.getEmail().equals(email)
                && customer.getPassword().equals(pwd));
    }
}