package ma.project.service;

import ma.project.classes.EmployeeTache;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class EmployeeTacheService implements IDao<EmployeeTache> {

    @Override
    public boolean create(EmployeeTache o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(o); // Garder persist car c'est une nouvelle entité
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(EmployeeTache o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(o);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(EmployeeTache o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            o = em.merge(o); // Important : merger d'abord l'entité détachée
            em.remove(o);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public EmployeeTache findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(EmployeeTache.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<EmployeeTache> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from EmployeeTache");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}