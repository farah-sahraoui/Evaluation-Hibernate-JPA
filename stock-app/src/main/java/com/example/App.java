package com.example;

import com.example.classes.Categorie;
import com.example.classes.Commande;
import com.example.classes.LigneCommandeProduit;
import com.example.classes.Produit;
import com.example.service.CategorieService;
import com.example.service.CommandeService;
import com.example.service.ProduitService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App {

    public static void main(String[] args) {

        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        CommandeService commandeService = new CommandeService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");

        // ============================================
        // 1. Affichage produits par catégorie
        // ============================================

        System.out.println("\n===== PRODUITS PAR CATEGORIE =====");

        Categorie categorie = categorieService.findById(1);

        if (categorie != null) {

            List<Produit> produits = produitService.findByCategorie(categorie);

            for (Produit p : produits) {

                System.out.println(
                        "Référence : " + p.getReference()
                                + " | Prix : " + p.getPrix() + " DH"
                );
            }
        }

        // ============================================
        // 2. Affichage produits prix > 100 DH
        // ============================================

        System.out.println("\n===== PRODUITS PRIX > 100 DH =====");

        List<Produit> produitsCher = produitService.findPrixSuperieur100();

        for (Produit p : produitsCher) {

            System.out.println(
                    "Référence : " + p.getReference()
                            + " | Prix : " + p.getPrix() + " DH"
            );
        }

        // ============================================
        // 3. Affichage produits d'une commande
        // ============================================

        System.out.println("\n===== PRODUITS D'UNE COMMANDE =====");

        Commande commande = commandeService.findById(1);

        if (commande != null) {

            System.out.println(
                    "\nCommande : " + commande.getId()
                            + "     Date : " + sdf.format(commande.getDate())
            );

            System.out.println("\nListe des produits :");
            System.out.println("Référence\tPrix\tQuantité");

            for (LigneCommandeProduit ligne : commande.getLignes()) {

                System.out.println(
                        ligne.getProduit().getReference() + "\t\t"
                                + ligne.getProduit().getPrix() + " DH\t"
                                + ligne.getQuantite()
                );
            }
        }

        // ============================================
        // 4. Affichage produits entre deux dates
        // ============================================

        System.out.println("\n===== PRODUITS ENTRE DEUX DATES =====");

        try {

            Date d1 = sdf.parse("01 January 2020");
            Date d2 = sdf.parse("31 December 2030");

            List<LigneCommandeProduit> lignes =
                    produitService.findProduitsEntreDates(d1, d2);

            for (LigneCommandeProduit ligne : lignes) {

                System.out.println(
                        "Commande : " + ligne.getCommande().getId()
                                + " | Produit : " + ligne.getProduit().getReference()
                                + " | Prix : " + ligne.getProduit().getPrix()
                                + " DH | Quantité : " + ligne.getQuantite()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n===== FIN =====");
    }
}