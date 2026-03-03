package ma.project.service;


import ma.project.classes.Employee;
import ma.project.classes.EmployeeTache;
import ma.project.classes.Projet;
import ma.project.classes.Tache;
import ma.project.dao.IDao;
import ma.project.util.HibernateUtil;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements IDao<Employee> {

    @Override
    public boolean create(Employee o) {
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
    public boolean update(Employee o) {
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
    public boolean delete(Employee o) {
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
    public Employee findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Employee> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery("from Employee");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Tache> getTachesRealiseesParEmploye(int employeeId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT et.tache FROM EmployeeTache et WHERE et.employee.id = :employeeId");
            query.setParameter("employeeId", employeeId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Projet> getProjetsGererParEmploye(int employeeId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT DISTINCT t.projet FROM EmployeeTache et " +
                            "JOIN et.tache t WHERE et.employee.id = :employeeId");
            query.setParameter("employeeId", employeeId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
