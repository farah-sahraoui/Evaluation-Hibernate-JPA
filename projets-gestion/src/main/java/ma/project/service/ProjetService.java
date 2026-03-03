package ma.project.service;

import ma.project.classes.Projet;
import ma.project.classes.Tache;
import ma.project.classes.EmployeeTache;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
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
    public boolean update(Projet o) {
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
    public boolean delete(Projet o) {
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
    public Projet findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Projet.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Projet> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Projet");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tache> getTachesPlanifieesPourProjet(int projetId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("FROM Tache t WHERE t.projet.id = :projetId");
            query.setParameter("projetId", projetId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void afficherTachesRealiseesAvecDates(int projetId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Projet projet = em.find(Projet.class, projetId);
            if(projet == null) {
                System.out.println("Projet non trouve");
                return;
            }

            System.out.println("Projet : " + projet.getId() + "      Nom : " + projet.getNom() +
                    "     Date debut : " + projet.getDateDebut());
            System.out.println("Liste des taches:");
            System.out.println("Num Nom            Date Debut Reelle   Date Fin Reelle");

            Query query = em.createQuery(
                    "SELECT et FROM EmployeeTache et WHERE et.tache.projet.id = :projetId");
            query.setParameter("projetId", projetId);
            List<EmployeeTache> employeeTaches = query.getResultList();

            for(EmployeeTache et : employeeTaches) {
                System.out.printf("%-4d %-15s %-20s %-15s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        et.getDateDebutReelle(),
                        et.getDateFinReelle());
            }
        } finally {
            em.close();
        }
    }
}
