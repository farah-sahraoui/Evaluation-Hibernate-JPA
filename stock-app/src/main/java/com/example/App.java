package com.example;

import com.example.classes.*;
import com.example.service.*;
import com.example.util.HibernateUtil;
import java.text.SimpleDateFormat;

public class App {
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            CategorieService catService = new CategorieService();
            ProduitService prodService = new ProduitService();
            CommandeService cmdService = new CommandeService();
            LigneCommandeService ligneService = new LigneCommandeService();

            System.out.println("Nettoyage de la base...");
            for(LigneCommandeProduit l : ligneService.findAll()) ligneService.delete(l);
            for(Produit p : prodService.findAll()) prodService.delete(p);
            for(Commande c : cmdService.findAll()) cmdService.delete(c);
            for(Categorie cat : catService.findAll()) catService.delete(cat);

            Categorie cat = new Categorie("INFO", "Informatique");
            catService.create(cat);

            Produit p1 = new Produit("PC01", 150, cat);
            prodService.create(p1);

            Commande cmd1 = new Commande(sdf.parse("02/03/2026"));
            cmdService.create(cmd1);

            LigneCommandeProduit lcp = new LigneCommandeProduit(cmd1, p1, 3);
            ligneService.create(lcp);

            System.out.println("\n--- RÉSULTATS ---");
            prodService.getProduitsPrixSuperieur100();
            prodService.afficherProduitsDansCommande(cmd1.getId());
            prodService.getProduitsCommandesEntreDates(
                    sdf.parse("01/03/2026"), sdf.parse("03/03/2026"));

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }
}