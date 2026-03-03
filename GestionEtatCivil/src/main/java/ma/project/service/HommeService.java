package ma.project.service;

import ma.project.beans.Homme;
import ma.project.beans.Mariage;
import ma.project.beans.Femme;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
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

    public Homme refresh(Homme homme) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.refresh(homme);
            return homme;
        } finally {
            em.close();
        }
    }

    public List<Femme> getEpousesEntreDates(int hommeId, Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT m.femme FROM Mariage m WHERE m.homme.id = :hommeId AND m.id.dateDebut BETWEEN :dateDebut AND :dateFin");
            query.setParameter("hommeId", hommeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void afficherMariagesHomme(int hommeId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Homme homme = em.find(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme non trouvé");
                return;
            }

            System.out.println("Nom : " + homme.getNom().toUpperCase() + " " + homme.getPrenom());

            Query query = em.createQuery(
                    "SELECT m FROM Mariage m JOIN FETCH m.femme WHERE m.homme.id = :hommeId ORDER BY m.id.dateDebut");
            query.setParameter("hommeId", hommeId);
            List<Mariage> tousMariages = query.getResultList();

            List<Mariage> mariagesEnCours = tousMariages.stream()
                    .filter(m -> m.getDateFin() == null).toList();
            List<Mariage> mariagesEchoues = tousMariages.stream()
                    .filter(m -> m.getDateFin() != null).toList();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("\nMariages En Cours :");
            if (mariagesEnCours.isEmpty()) {
                System.out.println("Aucun mariage en cours");
            } else {
                int i = 1;
                for (Mariage m : mariagesEnCours) {
                    System.out.printf("%d. Femme : %s %s   Date Début : %s    Nbr Enfants : %d%n",
                            i++,
                            m.getFemme().getNom().toUpperCase(),
                            m.getFemme().getPrenom().toUpperCase(),
                            sdf.format(m.getDateDebut()),
                            m.getNbrEnfant());
                }
            }

            System.out.println("\nMariages échoués :");
            if (mariagesEchoues.isEmpty()) {
                System.out.println("Aucun mariage échoué");
            } else {
                int i = 1;
                for (Mariage m : mariagesEchoues) {
                    System.out.printf("%d. Femme : %s %s   Date Début : %s   Date Fin : %s    Nbr Enfants : %d%n",
                            i++,
                            m.getFemme().getNom().toUpperCase(),
                            m.getFemme().getPrenom().toUpperCase(),
                            sdf.format(m.getDateDebut()),
                            sdf.format(m.getDateFin()),
                            m.getNbrEnfant());
                }
            }
        } finally {
            em.close();
        }
    }
}