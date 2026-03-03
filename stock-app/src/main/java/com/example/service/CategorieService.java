package com.example.service;

import com.example.classes.Categorie;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CategorieService implements IDao<Categorie> {

    @Override
    public boolean create(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.update(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.delete(o);

        tx.commit();
        session.close();

        return true;
    }

    @Override
    public Categorie findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Categorie c = session.get(Categorie.class, id);
        session.close();
        return c;
    }

    @Override
    public List<Categorie> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Categorie> categories = session.createQuery("from Categorie").list();
        session.close();
        return categories;
    }
}