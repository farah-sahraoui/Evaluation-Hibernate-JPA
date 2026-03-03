package com.example;

import com.example.util.HibernateUtil;

public class TestHibernate {

    public static void main(String[] args) {


        HibernateUtil.getSessionFactory();

        System.out.println("Connexion Hibernate réussie !");
    }
}