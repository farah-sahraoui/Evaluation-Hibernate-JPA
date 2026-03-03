package ma.project.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Mariage implements Serializable {

    @EmbeddedId
    private MariagePK id;

    @ManyToOne
    @MapsId("homme")
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @MapsId("femme")
    @JoinColumn(name = "femme_id")
    private Femme femme;

    @Temporal(TemporalType.DATE)
    private Date dateFin;

    private int nbrEnfant;

    public Mariage() {}

    public Mariage(Homme homme, Femme femme, Date dateDebut, Date dateFin, int nbrEnfant) {
        this.homme = homme;
        this.femme = femme;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
        this.id = new MariagePK(homme.getId(), femme.getId(), dateDebut);
    }

    public MariagePK getId() { return id; }
    public void setId(MariagePK id) { this.id = id; }
    public Homme getHomme() { return homme; }
    public void setHomme(Homme homme) { this.homme = homme; }
    public Femme getFemme() { return femme; }
    public void setFemme(Femme femme) { this.femme = femme; }
    public Date getDateDebut() { return id != null ? id.getDateDebut() : null; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int nbrEnfant) { this.nbrEnfant = nbrEnfant; }
}