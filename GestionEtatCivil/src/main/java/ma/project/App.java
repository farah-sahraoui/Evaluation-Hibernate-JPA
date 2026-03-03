package ma.project;

import ma.project.beans.Homme;
import ma.project.beans.Femme;
import ma.project.beans.Mariage;
import ma.project.service.HommeService;
import ma.project.service.FemmeService;
import ma.project.service.MariageService;
import ma.project.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            HommeService hommeService = new HommeService();
            FemmeService femmeService = new FemmeService();
            MariageService mariageService = new MariageService();

            System.out.println("==========================================");
            System.out.println("  APPLICATION DE GESTION D'ÉTAT CIVIL");
            System.out.println("==========================================\n");

            System.out.println("--- Nettoyage de la base de données ---");
            for(Mariage m : mariageService.findAll()) mariageService.delete(m);
            for(Femme f : femmeService.findAll()) femmeService.delete(f);
            for(Homme h : hommeService.findAll()) hommeService.delete(h);
            System.out.println("  ✓ Base de données nettoyée\n");

            System.out.println("=== Création des 10 femmes ===");
            Femme[] femmes = new Femme[10];
            femmes[0] = new Femme("ALAMI", "Fatima", "0612345678", "Casablanca", sdf.parse("15/03/1985"));
            femmes[1] = new Femme("RAMI", "Salima", "0623456789", "Rabat", sdf.parse("22/07/1988"));
            femmes[2] = new Femme("BENANI", "Khadija", "0634567890", "Fès", sdf.parse("10/11/1990"));
            femmes[3] = new Femme("TAZI", "Nadia", "0645678901", "Marrakech", sdf.parse("05/02/1983"));
            femmes[4] = new Femme("FASSI", "Leila", "0656789012", "Tanger", sdf.parse("18/09/1987"));
            femmes[5] = new Femme("ALAOUI", "Wafa", "0667890123", "Agadir", sdf.parse("30/04/1992"));
            femmes[6] = new Femme("CHRAIBI", "Samira", "0678901234", "Meknès", sdf.parse("12/12/1984"));
            femmes[7] = new Femme("BENNANI", "Amina", "0689012345", "Oujda", sdf.parse("25/06/1989"));
            femmes[8] = new Femme("EL IDRISSI", "Karima", "0690123456", "Laâyoune", sdf.parse("08/10/1982"));
            femmes[9] = new Femme("ZAHRAOUI", "Fatima Zahra", "0601234567", "Tétouan", sdf.parse("14/05/1993"));

            for (Femme f : femmes) {
                femmeService.create(f);
                System.out.println("  ✓ Femme créée: " + f.getPrenom() + " " + f.getNom());
            }

            System.out.println("\n=== Création des 5 hommes ===");
            Homme[] hommes = new Homme[5];
            hommes[0] = new Homme("SAFI", "Said", "0712345678", "Casablanca", sdf.parse("20/01/1980"));
            hommes[1] = new Homme("BENJELLOUN", "Hicham", "0723456789", "Rabat", sdf.parse("12/05/1982"));
            hommes[2] = new Homme("EL KABIR", "Youssef", "0734567890", "Fès", sdf.parse("03/09/1978"));
            hommes[3] = new Homme("TOUIMI", "Mehdi", "0745678901", "Marrakech", sdf.parse("28/11/1985"));
            hommes[4] = new Homme("BAHRI", "Amine", "0756789012", "Tanger", sdf.parse("07/04/1983"));

            for (Homme h : hommes) {
                hommeService.create(h);
                System.out.println("  ✓ Homme créé: " + h.getPrenom() + " " + h.getNom());
            }

            System.out.println("\n--- Rechargement des objets ---");
            for (int i = 0; i < hommes.length; i++) {
                hommes[i] = hommeService.findById(hommes[i].getId());
            }
            for (int i = 0; i < femmes.length; i++) {
                femmes[i] = femmeService.findById(femmes[i].getId());
            }
            System.out.println("  ✓ Objets rechargés avec succès");

            System.out.println("\n=== Création des mariages ===");
            Homme h0 = hommeService.findById(hommes[0].getId());
            Femme f1 = femmeService.findById(femmes[1].getId());
            Femme f2 = femmeService.findById(femmes[2].getId());
            Femme f5 = femmeService.findById(femmes[5].getId());
            Femme f8 = femmeService.findById(femmes[8].getId());


            mariageService.create(new Mariage(h0, f1, sdf.parse("03/09/1990"), null, 4));
            mariageService.create(new Mariage(h0, f2, sdf.parse("03/09/1995"), null, 2));
            mariageService.create(new Mariage(h0, f5, sdf.parse("04/11/2000"), null, 3));
            mariageService.create(new Mariage(h0, f8, sdf.parse("10/01/1993"), sdf.parse("15/12/1995"), 0));
            System.out.println("  ✓ 4 mariages pour SAFI Said");

            Homme h1 = hommeService.findById(hommes[1].getId());
            Femme f0 = femmeService.findById(femmes[0].getId());
            Femme f3 = femmeService.findById(femmes[3].getId());

            mariageService.create(new Mariage(h1, f0, sdf.parse("05/06/2010"), null, 2));
            mariageService.create(new Mariage(h1, f3, sdf.parse("12/09/2015"), sdf.parse("20/01/2020"), 1));
            System.out.println("  ✓ 2 mariages pour BENJELLOUN Hicham");


            mariageService.create(new Mariage(hommes[2], femmes[4], sdf.parse("08/04/2005"), null, 3));
            mariageService.create(new Mariage(hommes[2], femmes[6], sdf.parse("17/08/2012"), sdf.parse("30/06/2018"), 1));
            System.out.println("  ✓ 2 mariages pour EL KABIR Youssef");

            mariageService.create(new Mariage(hommes[3], femmes[7], sdf.parse("22/11/2018"), null, 1));
            mariageService.create(new Mariage(hommes[4], femmes[9], sdf.parse("14/02/2019"), null, 1));
            System.out.println("  ✓ 2 mariages pour les autres hommes");

            mariageService.create(new Mariage(hommes[2], femmes[1], sdf.parse("01/01/2020"), null, 1));
            System.out.println("  ✓ 1 mariage supplémentaire pour Salima RAMI");

            System.out.println("\n==========================================");
            System.out.println("  RÉSULTATS DES TESTS");
            System.out.println("==========================================\n");

            System.out.println("1. Liste des femmes :");
            System.out.println("----------------------------------------");
            List<Femme> toutesFemmes = femmeService.findAll();
            for (Femme f : toutesFemmes) {
                System.out.printf("  ID: %d | %s %s | Tél: %s | Née: %s%n",
                        f.getId(), f.getPrenom(), f.getNom(), f.getTelephone(),
                        sdf.format(f.getDateNaissance()));
            }

            System.out.println("\n2. Femme la plus âgée :");
            System.out.println("----------------------------------------");
            Femme plusAgee = femmeService.getFemmeLaPlusAgee();
            System.out.printf("  %s %s - Née le: %s%n",
                    plusAgee.getPrenom(), plusAgee.getNom(),
                    sdf.format(plusAgee.getDateNaissance()));

            System.out.println("\n3. Épouses de Said SAFI entre 1990 et 2000 :");
            System.out.println("----------------------------------------");
            List<Femme> epouses = hommeService.getEpousesEntreDates(hommes[0].getId(),
                    sdf.parse("01/01/1990"), sdf.parse("31/12/2000"));
            for (Femme f : epouses) {
                System.out.println("  ✓ " + f.getPrenom() + " " + f.getNom());
            }

            System.out.println("\n4. Nombre d'enfants de Salima RAMI entre 1990 et 2020 :");
            System.out.println("----------------------------------------");
            int nbrEnfants = femmeService.getNombreEnfantsEntreDates(femmes[1].getId(),
                    sdf.parse("01/01/1990"), sdf.parse("31/12/2020"));
            System.out.println("  Nombre d'enfants: " + nbrEnfants);

            System.out.println("\n5. Femmes mariées deux fois ou plus :");
            System.out.println("----------------------------------------");
            List<Femme> femmes2fois = femmeService.getFemmesMarieesAuMoinsDeuxFois();
            if (femmes2fois.isEmpty()) {
                System.out.println("  Aucune femme mariée 2 fois ou plus");
            } else {
                for (Femme f : femmes2fois) {
                    System.out.println("  ✓ " + f.getPrenom() + " " + f.getNom());
                }
            }

            System.out.println("\n6. Hommes mariés à 4 femmes entre 1990 et 2005 :");
            System.out.println("----------------------------------------");
            long nbrHommes = femmeService.getNombreHommesMarieesAQuatreFemmesEntreDates(
                    sdf.parse("01/01/1990"), sdf.parse("31/12/2005"));
            System.out.println("  Nombre d'hommes: " + nbrHommes);

            System.out.println("\n7. Détails des mariages de Said SAFI :");
            System.out.println("----------------------------------------");
            hommeService.afficherMariagesHomme(hommes[0].getId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }
}