package ma.project.service;


import ma.project.beans.Femme;
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

    // Requête native pour le nombre d'enfants d'une femme entre deux dates
    // Requête native pour le nombre d'enfants d'une femme entre deux dates
    public int getNombreEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createNativeQuery(
                    "SELECT COALESCE(SUM(m.nbrEnfant), 0) FROM mariage m " +
                            "WHERE m.femme_id = ? AND m.dateDebut BETWEEN ? AND ?");
            query.setParameter(1, femmeId);
            query.setParameter(2, dateDebut);
            query.setParameter(3, dateFin);

            Object result = query.getSingleResult();

            // Gestion des différents types possibles
            if (result instanceof BigDecimal) {
                return ((BigDecimal) result).intValue();
            } else if (result instanceof Long) {
                return ((Long) result).intValue();
            } else if (result instanceof Integer) {
                return (Integer) result;
            } else {
                return 0;
            }
        } finally {
            em.close();
        }
    }

    // Requête nommée pour les femmes mariées au moins deux fois
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createNamedQuery("Femme.findMarriedAtLeastTwice");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Méthode avec Criteria API pour le nombre d'hommes mariés à 4 femmes entre deux dates
    public long getNombreHommesMarieesAQuatreFemmesEntreDates(Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<ma.project.beans.Mariage> mariage = cq.from(ma.project.beans.Mariage.class);

            // Sélectionner les hommes et compter leurs mariages
            cq.select(cb.count(mariage.get("homme").get("id")))
                    .where(cb.and(
                            cb.greaterThanOrEqualTo(mariage.get("dateDebut"), dateDebut),
                            cb.lessThanOrEqualTo(mariage.get("dateDebut"), dateFin)
                    ))
                    .groupBy(mariage.get("homme").get("id"))
                    .having(cb.equal(cb.count(mariage.get("id")), 4));

            return em.createQuery(cq).getResultList().size();
        } finally {
            em.close();
        }
    }

    // Méthode pour trouver la femme la plus âgée
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