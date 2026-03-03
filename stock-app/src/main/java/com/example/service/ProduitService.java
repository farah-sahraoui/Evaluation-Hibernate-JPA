package com.example.service;


import com.example.classes.Produit;
import com.example.classes.Categorie;
import com.example.classes.Commande;
import com.example.classes.LigneCommandeProduit;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(Produit o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(o);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(Produit o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(o) ? o : em.merge(o));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public Produit findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Produit.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produit> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Produit");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Produit> getProduitsParCategorie(int categorieId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("FROM Produit p WHERE p.categorie.id = :id");
            query.setParameter("id", categorieId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void getProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            System.out.println("\n==== PRODUCTS ENTRE DEUX DATES ====");

            Query query = em.createQuery(
                    "SELECT l FROM LigneCommandeProduit l WHERE l.commande.date BETWEEN :debut AND :fin");
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);

            List<LigneCommandeProduit> lignes = query.getResultList();

            for(LigneCommandeProduit l : lignes) {
                System.out.println("Commande : " + l.getCommande().getId() +
                        " | Produit : " + l.getProduit().getReference() +
                        " | Prix : " + l.getProduit().getPrix() + " DH" +
                        " | Quantité : " + l.getQuantite());
            }
        } finally {
            em.close();
        }
    }

    public void afficherProduitsDansCommande(int commandeId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Commande commande = em.find(Commande.class, commandeId);
            if(commande == null) {
                System.out.println("Commande non trouvée");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            System.out.println("\n==== PRODUCTS D'UNE COMMANDE ====");
            System.out.println("Commande : " + commande.getId() + "    Date : " + sdf.format(commande.getDate()));
            System.out.println("\nListe des produits :");
            System.out.println("Référence   Prix    Quantité");

            Query query = em.createQuery(
                    "SELECT l FROM LigneCommandeProduit l WHERE l.commande.id = :id");
            query.setParameter("id", commandeId);
            List<LigneCommandeProduit> lignes = query.getResultList();

            for(LigneCommandeProduit l : lignes) {
                System.out.printf("%-10s %-7.0f DH    %d%n",
                        l.getProduit().getReference(),
                        l.getProduit().getPrix(),
                        l.getQuantite());
            }
        } finally {
            em.close();
        }
    }

    public void getProduitsPrixSuperieur100() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            System.out.println("==== PRODUCTS PRIX > 100 DH ====");
            Query query = em.createNamedQuery("Produit.prixSuperieur100");
            List<Produit> produits = query.getResultList();

            for(Produit p : produits) {
                System.out.println("Référence : " + p.getReference() + " | Prix : " + p.getPrix() + " DH");
            }
        } finally {
            em.close();
        }
    }
}