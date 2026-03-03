package ma.project.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EmployeeTache {

    @EmbeddedId
    private ma.project.classes.EmployeeTachePK id;

    @ManyToOne
    @MapsId("employee")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("tache")
    @JoinColumn(name = "tache_id")
    private ma.project.classes.Tache tache;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    public EmployeeTache() {}

    public EmployeeTache(Employee employee, ma.project.classes.Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.employee = employee;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.id = new ma.project.classes.EmployeeTachePK(employee.getId(), tache.getId());
    }

    public ma.project.classes.EmployeeTachePK getId() { return id; }
    public void setId(ma.project.classes.EmployeeTachePK id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public ma.project.classes.Tache getTache() { return tache; }
    public void setTache(ma.project.classes.Tache tache) { this.tache = tache; }
    public Date getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(Date dateDebutReelle) { this.dateDebutReelle = dateDebutReelle; }
    public Date getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(Date dateFinReelle) { this.dateFinReelle = dateFinReelle; }
}