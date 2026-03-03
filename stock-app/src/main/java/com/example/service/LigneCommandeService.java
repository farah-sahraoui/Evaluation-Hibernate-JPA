package com.example.service;

import com.example.classes.LigneCommandeProduit;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import javax.persistence.*;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Override
    public boolean create(LigneCommandeProduit o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Merge est plus sûr pour les entités déjà existantes (Commande/Produit)
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
    public boolean delete(LigneCommandeProduit o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // On rattache l'objet avant de le supprimer
            em.remove(em.contains(o) ? o : em.merge(o));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Les autres méthodes (update, findById) restent similaires...
    @Override public boolean update(LigneCommandeProduit o) { return create(o); }
    @Override public LigneCommandeProduit findById(int id) { return null; } // Nécessite PK en param normalement
}