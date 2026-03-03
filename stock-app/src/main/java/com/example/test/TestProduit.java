
package com.example.test;

import com.example.classes.Categorie;
import com.example.classes.Produit;
import com.example.service.CategorieService;
import com.example.service.ProduitService;

public class TestProduit {

    public static void main(String[] args) {

        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();

        Categorie c = cs.findById(1);

        Produit p = new Produit();
        p.setReference("PC01");
        p.setPrix(150);
        p.setCategorie(c);

        ps.create(p);

        System.out.println("Produit ajouté !");
    }
}
