package ma.project.service;

import ma.project.beans.Mariage;
import ma.project.beans.MariagePK;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MariageService implements IDao<Mariage> {

    @Override
    public boolean create(Mariage o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (!em.contains(o.getHomme())) {
                o.setHomme(em.merge(o.getHomme()));
            }
            if (!em.contains(o.getFemme())) {
                o.setFemme(em.merge(o.getFemme()));
            }

            em.persist(o);
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
    public boolean update(Mariage o) {
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
    public boolean delete(Mariage o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            o = em.merge(o);
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
    public Mariage findById(int id) {
        throw new UnsupportedOperationException("Utilisez findById(MariagePK id) à la place");
    }

    public Mariage findById(MariagePK id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Mariage.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mariage> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Mariage");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}