package com.example.classes;

import javax.persistence.*;

@Entity
public class LigneCommandeProduit {

    @EmbeddedId
    private LigneCommandeProduitPK id = new LigneCommandeProduitPK();

    @ManyToOne
    @MapsId("commande")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne
    @MapsId("produit")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    private int quantite;

    public LigneCommandeProduit() {}

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        // Synchronisation de la clé composée
        this.id = new LigneCommandeProduitPK(commande.getId(), produit.getId());
    }

    public LigneCommandeProduitPK getId() { return id; }
    public void setId(LigneCommandeProduitPK id) { this.id = id; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}