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
    public boolean update(LigneCommandeProduit o) {
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
    public boolean delete(LigneCommandeProduit o) {
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
    public LigneCommandeProduit findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(LigneCommandeProduit.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from LigneCommandeProduit");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}