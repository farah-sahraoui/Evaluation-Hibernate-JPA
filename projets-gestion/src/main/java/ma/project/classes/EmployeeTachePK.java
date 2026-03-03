package ma.project.classes;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeTachePK implements Serializable {
    private int employee;
    private int tache;

    public EmployeeTachePK() {}

    public EmployeeTachePK(int employee, int tache) {
        this.employee = employee;
        this.tache = tache;
    }

    public int getEmployee() { return employee; }
    public void setEmployee(int employee) { this.employee = employee; }
    public int getTache() { return tache; }
    public void setTache(int tache) { this.tache = tache; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTachePK that = (EmployeeTachePK) o;
        return employee == that.employee && tache == that.tache;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, tache);
    }
}