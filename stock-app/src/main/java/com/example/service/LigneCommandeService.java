package com.example.service;

import com.example.classes.LigneCommandeProduit;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Override
    public boolean create(LigneCommandeProduit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(LigneCommandeProduit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(LigneCommandeProduit o) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public LigneCommandeProduit findById(int id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        LigneCommandeProduit l =
                session.get(LigneCommandeProduit.class, id);

        session.close();

        return l;
    }

    @Override
    public List<LigneCommandeProduit> findAll() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommandeProduit> lignes =
                session.createQuery("from LigneCommandeProduit").list();

        session.close();

        return lignes;
    }
}