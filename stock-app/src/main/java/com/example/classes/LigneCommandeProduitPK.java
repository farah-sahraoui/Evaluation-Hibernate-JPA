package com.example.classes;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LigneCommandeProduitPK implements Serializable {
    // Vérifiez que Commande.id et Produit.id sont bien des 'int'
    private int commande;
    private int produit;

    public LigneCommandeProduitPK() {}

    public LigneCommandeProduitPK(int commande, int produit) {
        this.commande = commande;
        this.produit = produit;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LigneCommandeProduitPK that = (LigneCommandeProduitPK) o;
        return commande == that.commande && produit == that.produit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commande, produit);
    }
}