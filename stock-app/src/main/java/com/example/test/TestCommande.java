package com.example.test;

import com.example.classes.*;
import com.example.service.*;

import java.util.Date;

public class TestCommande {

    public static void main(String[] args) {

        ProduitService ps = new ProduitService();
        CommandeService cs = new CommandeService();
        LigneCommandeService ls = new LigneCommandeService();

        Produit p = ps.findById(1);

        Commande c = new Commande();
        c.setDate(new Date());

        cs.create(c);

        LigneCommandeProduit l = new LigneCommandeProduit();
        l.setCommande(c);
        l.setProduit(p);
        l.setQuantite(3);

        ls.create(l);

        System.out.println("Commande créée !");
    }
}
