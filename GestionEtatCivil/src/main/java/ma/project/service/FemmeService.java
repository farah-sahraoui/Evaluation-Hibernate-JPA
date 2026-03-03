package ma.project.service;


import ma.project.beans.Femme;
import ma.project.beans.Mariage;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
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
    public boolean update(Femme o) {
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
    public boolean delete(Femme o) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(o) ? o : em.merge(o));
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
    public Femme findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Femme.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Femme> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Femme");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int getNombreEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COALESCE(SUM(m.nbrEnfant), 0) FROM Mariage m WHERE m.femme.id = :femmeId AND m.id.dateDebut BETWEEN :dateDebut AND :dateFin");
            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);

            Long result = (Long) query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } finally {
            em.close();
        }
    }

    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createNamedQuery("Femme.findMarriedAtLeastTwice");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long getNombreHommesMarieesAQuatreFemmesEntreDates(Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT m.homme.id FROM Mariage m " +
                            "WHERE m.id.dateDebut BETWEEN :dateDebut AND :dateFin " +
                            "GROUP BY m.homme.id " +
                            "HAVING COUNT(m) = 4");
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);

            List<Integer> result = query.getResultList();
            return result.size();
        } finally {
            em.close();
        }
    }

    public Femme getFemmeLaPlusAgee() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("FROM Femme ORDER BY dateNaissance ASC");
            query.setMaxResults(1);
            return (Femme) query.getSingleResult();
        } finally {
            em.close();
        }
    }
}