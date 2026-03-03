package com.example.service;

import com.example.classes.Commande;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public boolean create(Commande o) {
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
    public boolean update(Commande o) {
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
    public boolean delete(Commande o) {
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
    public Commande findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Commande.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Commande> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Commande");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
