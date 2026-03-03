package ma.project.beans;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MariagePK implements Serializable {
    private int homme;
    private int femme;
    private java.util.Date dateDebut;

    public MariagePK() {}

    public MariagePK(int homme, int femme, java.util.Date dateDebut) {
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
    }

    public int getHomme() { return homme; }
    public void setHomme(int homme) { this.homme = homme; }
    public int getFemme() { return femme; }
    public void setFemme(int femme) { this.femme = femme; }
    public java.util.Date getDateDebut() { return dateDebut; }
    public void setDateDebut(java.util.Date dateDebut) { this.dateDebut = dateDebut; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MariagePK that = (MariagePK) o;
        return homme == that.homme && femme == that.femme &&
                Objects.equals(dateDebut, that.dateDebut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homme, femme, dateDebut);
    }
}