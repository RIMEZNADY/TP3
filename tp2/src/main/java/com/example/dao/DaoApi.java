package com.example.dao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("daoApi")
@Profile("api")  // Actif uniquement avec le profil "api"
public class DaoApi implements IDao {
    @Override
    public double getValue() {
        System.out.println("Version API (appel service web)");
        return 220.0;
    }
}