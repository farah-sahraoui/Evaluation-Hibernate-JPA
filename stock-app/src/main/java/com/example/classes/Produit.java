package com.example.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "produit")
@NamedQuery(
        name = "Produit.prixSuperieur100",
        query = "from Produit p where p.prix > 100"
)
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;

    private float prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    public Produit() {}

    public int getId() { return id; }

    public String getReference() { return reference; }

    public void setReference(String reference) { this.reference = reference; }

    public float getPrix() { return prix; }

    public void setPrix(float prix) { this.prix = prix; }

    public Categorie getCategorie() { return categorie; }

    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}