package com.example.service;

import com.example.classes.Categorie;
import com.example.classes.LigneCommandeProduit;
import com.example.classes.Produit;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(Produit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(Produit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public Produit findById(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Produit p = session.get(Produit.class, id);
        session.close();

        return p;
    }

    @Override
    public List<Produit> findAll() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery("from Produit").list();
        session.close();

        return produits;
    }

    // ===============================
    // produits par categorie
    // ===============================

    public List<Produit> findByCategorie(Categorie categorie) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Produit> produits = session.createQuery(
                        "from Produit p where p.categorie.id = :id")
                .setParameter("id", categorie.getId())
                .list();

        session.close();

        return produits;
    }

    // ===============================
    // produits prix > 100 (NamedQuery)
    // ===============================

    public List<Produit> findPrixSuperieur100() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Produit> produits =
                session.createNamedQuery("Produit.prixSuperieur100").list();

        session.close();

        return produits;
    }
    public List<LigneCommandeProduit> findProduitsEntreDates(Date d1, Date d2) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<LigneCommandeProduit> list =
                session.createQuery(
                                "from LigneCommandeProduit l where l.commande.date between :d1 and :d2")
                        .setParameter("d1", d1)
                        .setParameter("d2", d2)
                        .list();

        session.close();

        return list;
    }public List<LigneCommandeProduit> findProduitsByCommande(int commandeId) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<LigneCommandeProduit> list =
                session.createQuery(
                                "from LigneCommandeProduit l where l.commande.id = :id")
                        .setParameter("id", commandeId)
                        .list();

        session.close();

        return list;
    }
}