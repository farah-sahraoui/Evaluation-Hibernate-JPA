package ma.project.service;


import ma.project.classes.Tache;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Tache.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Tache> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Tache");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tache> getTachesPrixSuperieurA1000() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createNamedQuery("Tache.prixSuperieurA1000");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tache> getTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT et.tache FROM EmployeeTache et " +
                            "WHERE et.dateDebutReelle >= :dateDebut AND et.dateFinReelle <= :dateFin");
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
