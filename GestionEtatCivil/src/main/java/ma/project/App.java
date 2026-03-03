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

            System.out.println("=== TEST APPLICATION ETAT CIVIL ===\n");

            for(Mariage m : mariageService.findAll()) mariageService.delete(m);
            for(Femme f : femmeService.findAll()) femmeService.delete(f);
            for(Homme h : hommeService.findAll()) hommeService.delete(h);

            Femme[] femmes = new Femme[10];
            femmes[0] = new Femme("ALAMI", "Fatima", "0612345678", "Casablanca", sdf.parse("15/03/1985"));
            femmes[1] = new Femme("RAMI", "Salima", "0623456789", "Rabat", sdf.parse("22/07/1988"));
            femmes[2] = new Femme("BENANI", "Khadija", "0634567890", "Fes", sdf.parse("10/11/1990"));
            femmes[3] = new Femme("TAZI", "Nadia", "0645678901", "Marrakech", sdf.parse("05/02/1983"));
            femmes[4] = new Femme("FASSI", "Leila", "0656789012", "Tanger", sdf.parse("18/09/1987"));
            femmes[5] = new Femme("ALAOUI", "Wafa", "0667890123", "Agadir", sdf.parse("30/04/1992"));
            femmes[6] = new Femme("CHRAIBI", "Samira", "0678901234", "Meknes", sdf.parse("12/12/1984"));
            femmes[7] = new Femme("BENNANI", "Amina", "0689012345", "Oujda", sdf.parse("25/06/1989"));
            femmes[8] = new Femme("EL IDRISSI", "Karima", "0690123456", "Laayoune", sdf.parse("08/10/1982"));
            femmes[9] = new Femme("ZAHRAOUI", "Fatima Zahra", "0601234567", "Tetouan", sdf.parse("14/05/1993"));

            for(Femme f : femmes) femmeService.create(f);

            Homme[] hommes = new Homme[5];
            hommes[0] = new Homme("SAFI", "Said", "0712345678", "Casablanca", sdf.parse("20/01/1980"));
            hommes[1] = new Homme("BENJELLOUN", "Hicham", "0723456789", "Rabat", sdf.parse("12/05/1982"));
            hommes[2] = new Homme("EL KABIR", "Youssef", "0734567890", "Fes", sdf.parse("03/09/1978"));
            hommes[3] = new Homme("TOUIMI", "Mehdi", "0745678901", "Marrakech", sdf.parse("28/11/1985"));
            hommes[4] = new Homme("BAHRI", "Amine", "0756789012", "Tanger", sdf.parse("07/04/1983"));

            for(Homme h : hommes) hommeService.create(h);

            mariageService.create(new Mariage(hommes[0], femmes[1], sdf.parse("03/09/1990"), null, 4));
            mariageService.create(new Mariage(hommes[0], femmes[2], sdf.parse("03/09/1995"), null, 2));
            mariageService.create(new Mariage(hommes[0], femmes[5], sdf.parse("04/11/2000"), null, 3));
            mariageService.create(new Mariage(hommes[0], femmes[8], sdf.parse("10/01/1993"), sdf.parse("15/12/1995"), 0));
            mariageService.create(new Mariage(hommes[1], femmes[0], sdf.parse("05/06/2010"), null, 2));
            mariageService.create(new Mariage(hommes[1], femmes[3], sdf.parse("12/09/2015"), sdf.parse("20/01/2020"), 1));
            mariageService.create(new Mariage(hommes[2], femmes[4], sdf.parse("08/04/2005"), null, 3));
            mariageService.create(new Mariage(hommes[2], femmes[6], sdf.parse("17/08/2012"), sdf.parse("30/06/2018"), 1));
            mariageService.create(new Mariage(hommes[3], femmes[7], sdf.parse("22/11/2018"), null, 1));
            mariageService.create(new Mariage(hommes[4], femmes[9], sdf.parse("14/02/2019"), null, 1));
            mariageService.create(new Mariage(hommes[2], femmes[1], sdf.parse("01/01/2020"), null, 1));

            List<Femme> toutesFemmes = femmeService.findAll();
            List<Homme> tousHommes = hommeService.findAll();

            Homme saidSafi = null;
            for(Homme h : tousHommes) {
                if(h.getNom().equals("SAFI") && h.getPrenom().equals("Said")) saidSafi = h;
            }

            Femme salimaRami = null;
            for(Femme f : toutesFemmes) {
                if(f.getNom().equals("RAMI") && f.getPrenom().equals("Salima")) salimaRami = f;
            }

            System.out.println("1. Liste des femmes:");
            for(Femme f : toutesFemmes) {
                System.out.println("   " + f.getPrenom() + " " + f.getNom() + " - " + sdf.format(f.getDateNaissance()));
            }

            System.out.println("\n2. Femme la plus agee:");
            Femme plusAgee = femmeService.getFemmeLaPlusAgee();
            System.out.println("   " + plusAgee.getPrenom() + " " + plusAgee.getNom() + " - " + sdf.format(plusAgee.getDateNaissance()));

            if(saidSafi != null) {
                System.out.println("\n3. Epouses de Said SAFI (1990-2000):");
                List<Femme> epouses = hommeService.getEpousesEntreDates(saidSafi.getId(), sdf.parse("01/01/1990"), sdf.parse("31/12/2000"));
                for(Femme f : epouses) System.out.println("   - " + f.getPrenom() + " " + f.getNom());
            }

            if(salimaRami != null) {
                System.out.println("\n4. Enfants de Salima RAMI (1990-2020): " +
                        femmeService.getNombreEnfantsEntreDates(salimaRami.getId(), sdf.parse("01/01/1990"), sdf.parse("31/12/2020")));
            }

            System.out.println("\n5. Femmes mariees 2 fois ou plus:");
            List<Femme> femmes2fois = femmeService.getFemmesMarieesAuMoinsDeuxFois();
            for(Femme f : femmes2fois) System.out.println("   - " + f.getPrenom() + " " + f.getNom());

            System.out.println("\n6. Hommes maries a 4 femmes (1990-2005): " +
                    femmeService.getNombreHommesMarieesAQuatreFemmesEntreDates(sdf.parse("01/01/1990"), sdf.parse("31/12/2005")));

            if(saidSafi != null) {
                System.out.println("\n7. Details mariages Said SAFI:");
                hommeService.afficherMariagesHomme(saidSafi.getId());
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.close();
        }
    }
}