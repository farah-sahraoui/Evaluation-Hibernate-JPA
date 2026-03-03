package ma.project.service;


import ma.project.beans.Homme;
import ma.project.beans.Mariage;
import ma.project.beans.Femme;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public Homme findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Homme.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Homme> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Homme");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Méthode pour afficher les épouses d'un homme entre deux dates
    public List<Femme> getEpousesEntreDates(int hommeId, Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT m.femme FROM Mariage m WHERE m.homme.id = :hommeId " +
                            "AND m.dateDebut BETWEEN :dateDebut AND :dateFin");
            query.setParameter("hommeId", hommeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Méthode pour afficher les mariages d'un homme avec détails
    public void afficherMariagesHomme(int hommeId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Homme homme = em.find(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme non trouvé");
                return;
            }

            System.out.println("Nom : " + homme.getNom().toUpperCase() + " " + homme.getPrenom());

            // Mariages en cours (sans date de fin)
            Query queryEnCours = em.createQuery(
                    "FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NULL");
            queryEnCours.setParameter("hommeId", hommeId);
            List<Mariage> mariagesEnCours = queryEnCours.getResultList();

            System.out.println("\nMariages En Cours :");
            int i = 1;
            for (Mariage m : mariagesEnCours) {
                System.out.println(i + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                        "   Date Début : " + m.getDateDebut() + "    Nbr Enfants : " + m.getNbrEnfant());
                i++;
            }

            // Mariages échoués (avec date de fin)
            Query queryEchoues = em.createQuery(
                    "FROM Mariage m WHERE m.homme.id = :hommeId AND m.dateFin IS NOT NULL");
            queryEchoues.setParameter("hommeId", hommeId);
            List<Mariage> mariagesEchoues = queryEchoues.getResultList();

            System.out.println("\nMariages échoués :");
            i = 1;
            for (Mariage m : mariagesEchoues) {
                System.out.println(i + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom() +
                        "   Date Début : " + m.getDateDebut() +
                        "   Date Fin : " + m.getDateFin() +
                        "    Nbr Enfants : " + m.getNbrEnfant());
                i++;
            }
        } finally {
            em.close();
        }
    }
}