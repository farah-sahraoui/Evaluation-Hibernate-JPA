package ma.project.beans;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
public class Homme extends ma.project.beans.Personne {

    @OneToMany(mappedBy = "homme")
    private List<Mariage> mariages;

    public Homme() { super(); }

    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}