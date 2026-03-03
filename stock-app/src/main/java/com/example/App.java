package com.example;

import com.example.classes.*;
import com.example.service.*;
import com.example.util.HibernateUtil;
import java.text.SimpleDateFormat;

public class App {
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            CategorieService categorieService = new CategorieService();
            ProduitService produitService = new ProduitService();
            CommandeService commandeService = new CommandeService();
            LigneCommandeService ligneService = new LigneCommandeService();

            for(LigneCommandeProduit l : ligneService.findAll()) ligneService.delete(l);
            for(Produit p : produitService.findAll()) produitService.delete(p);
            for(Commande c : commandeService.findAll()) commandeService.delete(c);
            for(Categorie cat : categorieService.findAll()) categorieService.delete(cat);

            Categorie cat = new Categorie("INFO", "Informatique");
            categorieService.create(cat);

            Produit p1 = new Produit("PC01", 150, cat);
            produitService.create(p1);

            Commande cmd1 = new Commande(sdf.parse("02/03/2026"));
            commandeService.create(cmd1);

            cmd1 = commandeService.findById(cmd1.getId());
            p1 = produitService.findById(p1.getId());


            ligneService.create(new LigneCommandeProduit(cmd1, p1, 3));

            produitService.getProduitsPrixSuperieur100();

            produitService.afficherProduitsDansCommande(cmd1.getId());

            produitService.getProduitsCommandesEntreDates(
                    sdf.parse("01/03/2026"), sdf.parse("03/03/2026"));

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }
}