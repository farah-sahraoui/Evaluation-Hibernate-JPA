package com.example.test;

import com.example.classes.Categorie;
import com.example.service.CategorieService;

public class TestCategorie {

    public static void main(String[] args) {

        CategorieService cs = new CategorieService();

        Categorie c1 = new Categorie();
        c1.setCode("CAT02");
        c1.setLibelle("Electroménager");

        cs.create(c1);

        System.out.println("Categorie ajoutée !");
    }
}