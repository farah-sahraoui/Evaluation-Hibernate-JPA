package ma.project.beans;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Femme.findMarriedAtLeastTwice",
                query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2")
})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme")
    private List<Mariage> mariages;

    // Constructeur par défaut OBLIGATOIRE
    public Femme() {
        super();
    }

    // Constructeur avec paramètres
    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}