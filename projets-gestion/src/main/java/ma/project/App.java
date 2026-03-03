package ma.project;

import ma.project.classes.*;
import ma.project.service.*;
import ma.project.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            EmployeeService employeeService = new EmployeeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeeTacheService etService = new EmployeeTacheService();

            System.out.println("=== TEST GESTION PROJETS ===\n");

            for(EmployeeTache et : etService.findAll()) etService.delete(et);
            for(Tache t : tacheService.findAll()) tacheService.delete(t);
            for(Projet p : projetService.findAll()) projetService.delete(p);
            for(Employee e : employeeService.findAll()) employeeService.delete(e);

            Employee e1 = new Employee("ALAMI", "Ahmed", "0612345678");
            Employee e2 = new Employee("RAMI", "Said", "0623456789");
            Employee e3 = new Employee("BENANI", "Fatima", "0634567890");

            employeeService.create(e1);
            employeeService.create(e2);
            employeeService.create(e3);
            System.out.println("3 employes crees");

            Projet p1 = new Projet("Gestion de stock", sdf.parse("14/01/2013"), sdf.parse("30/04/2013"));
            Projet p2 = new Projet("Site web", sdf.parse("01/02/2014"), sdf.parse("30/06/2014"));

            projetService.create(p1);
            projetService.create(p2);
            System.out.println("2 projets crees");

            Tache t1 = new Tache("Analyse", sdf.parse("10/02/2013"), sdf.parse("20/02/2013"), 800, p1);
            Tache t2 = new Tache("Conception", sdf.parse("10/03/2013"), sdf.parse("15/03/2013"), 1200, p1);
            Tache t3 = new Tache("Developpement", sdf.parse("10/04/2013"), sdf.parse("25/04/2013"), 2000, p1);
            Tache t4 = new Tache("Design", sdf.parse("05/02/2014"), sdf.parse("20/02/2014"), 1500, p2);
            Tache t5 = new Tache("Programmation", sdf.parse("01/03/2014"), sdf.parse("15/05/2014"), 3000, p2);

            tacheService.create(t1);
            tacheService.create(t2);
            tacheService.create(t3);
            tacheService.create(t4);
            tacheService.create(t5);
            System.out.println("5 taches creees");

            e1 = employeeService.findById(e1.getId());
            e2 = employeeService.findById(e2.getId());
            e3 = employeeService.findById(e3.getId());
            t1 = tacheService.findById(t1.getId());
            t2 = tacheService.findById(t2.getId());
            t3 = tacheService.findById(t3.getId());
            t4 = tacheService.findById(t4.getId());
            t5 = tacheService.findById(t5.getId());

            etService.create(new EmployeeTache(e1, t1, sdf.parse("10/02/2013"), sdf.parse("20/02/2013")));
            etService.create(new EmployeeTache(e1, t2, sdf.parse("10/03/2013"), sdf.parse("15/03/2013")));
            etService.create(new EmployeeTache(e2, t3, sdf.parse("10/04/2013"), sdf.parse("25/04/2013")));
            etService.create(new EmployeeTache(e2, t4, sdf.parse("05/02/2014"), sdf.parse("20/02/2014")));
            etService.create(new EmployeeTache(e3, t5, sdf.parse("01/03/2014"), sdf.parse("15/05/2014")));
            System.out.println("5 affectations creees\n");

            System.out.println("1. Taches realisees par Ahmed ALAMI:");
            List<Tache> tachesE1 = employeeService.getTachesRealiseesParEmploye(e1.getId());
            for(Tache t : tachesE1) {
                System.out.println("   - " + t.getNom());
            }

            System.out.println("\n2. Projets geres par Ahmed ALAMI:");
            List<Projet> projetsE1 = employeeService.getProjetsGererParEmploye(e1.getId());
            for(Projet p : projetsE1) {
                System.out.println("   - " + p.getNom());
            }

            System.out.println("\n3. Taches planifiees pour projet Gestion de stock:");
            List<Tache> tachesP1 = projetService.getTachesPlanifieesPourProjet(p1.getId());
            for(Tache t : tachesP1) {
                System.out.println("   - " + t.getNom() + " (" + sdf.format(t.getDateDebut()) + " - " + sdf.format(t.getDateFin()) + ")");
            }

            System.out.println("\n4. Taches avec prix > 1000 DH:");
            List<Tache> tachesChere = tacheService.getTachesPrixSuperieurA1000();
            for(Tache t : tachesChere) {
                System.out.println("   - " + t.getNom() + " : " + t.getPrix() + " DH");
            }

            System.out.println("\n5. Taches realisees entre 01/01/2013 et 31/12/2013:");
            List<Tache> taches2013 = tacheService.getTachesRealiseesEntreDates(
                    sdf.parse("01/01/2013"), sdf.parse("31/12/2013"));
            for(Tache t : taches2013) {
                System.out.println("   - " + t.getNom());
            }

            System.out.println("\n6. Affichage detaille du projet Gestion de stock:");
            projetService.afficherTachesRealiseesAvecDates(p1.getId());

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }
}