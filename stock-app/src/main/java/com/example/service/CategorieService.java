package com.example.service;

import com.example.classes.Categorie;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CategorieService implements IDao<Categorie> {

    @Override
    public boolean create(Categorie o) {
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
    public boolean update(Categorie o) {
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
    public boolean delete(Categorie o) {
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
    public Categorie findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Categorie.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Categorie> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Categorie");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}