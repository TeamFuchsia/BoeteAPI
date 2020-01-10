package nl.fuchsia.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusnr;

    @Column
    private String omschrijving;

    @OneToMany(mappedBy = "status", cascade = {CascadeType.PERSIST})
    private List<ZaakStatus> zaakStatus;

    public Status() {
    }

    public Status(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Status(int statusnr, String omschrijving) {
        this.statusnr = statusnr;
        this.omschrijving = omschrijving;
    }

    public Status(int statusnr, String omschrijving, List<ZaakStatus> zaakStatus) {
        this.statusnr = statusnr;
        this.omschrijving = omschrijving;
        this.zaakStatus = zaakStatus;
    }

    public int getStatusnr() {
        return statusnr;
    }

    public void setStatusnr(int statusnr) {
        this.statusnr = statusnr;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public List<ZaakStatus> getZaakStatus() {
        return zaakStatus;
    }

    public void setZaakStatus(List<ZaakStatus> zaakStatus) {
        this.zaakStatus = zaakStatus;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusnr=" + statusnr +
                ", omschrijving='" + omschrijving + '\'' +
                ", zaakStatus=" + zaakStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return statusnr == status.statusnr &&
                Objects.equals(omschrijving, status.omschrijving) &&
                Objects.equals(zaakStatus, status.zaakStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusnr, omschrijving, zaakStatus);
    }
}


