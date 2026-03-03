package com.example.service;

import com.example.classes.Commande;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public boolean create(Commande o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(Commande o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(Commande o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public Commande findById(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Commande c = session.get(Commande.class, id);
        session.close();

        return c;
    }

    @Override
    public List<Commande> findAll() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Commande> commandes =
                session.createQuery("from Commande").list();

        session.close();

        return commandes;
    }
}
