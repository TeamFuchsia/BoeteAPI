package nl.fuchsia.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "zaak")
public class Zaak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer zaaknr;

    @Column
    private LocalDate overtredingsdatum;

    @Column
    // mag leeg zijn indien het adminstratieve boete is bijv boete niet verzekerd.
    private String pleeglocatie;

    @ManyToOne
    @JoinColumn(name = "persoonnr")
    private Persoon persoon;

    @ManyToMany
    @JoinTable(name = "zaakregel", joinColumns = @JoinColumn(name = "zaaknr", referencedColumnName = "zaaknr"), inverseJoinColumns = @JoinColumn(name = "feitnr", referencedColumnName = "feitnr"))
    private List<Feit> feiten;

    @OneToMany(mappedBy = "zaak", cascade = {CascadeType.PERSIST})
    private List<ZaakStatus> zaakstatus;

    public Zaak() {
    }

    public Zaak(LocalDate overtredingsdatum, String pleeglocatie) {
        this.overtredingsdatum = overtredingsdatum;
        this.pleeglocatie = pleeglocatie;
    }

    public Zaak(Integer zaaknr, LocalDate overtredingsdatum, String pleeglocatie) {
        this(overtredingsdatum, pleeglocatie);
        this.zaaknr = zaaknr;
    }

    public Zaak(Integer zaaknr, LocalDate overtredingsdatum, String pleeglocatie, Persoon persoon, List<Feit> feiten) {
        this(overtredingsdatum, pleeglocatie);
        this.feiten = feiten;
        this.zaaknr = zaaknr;
        this.persoon = persoon;
    }

    public Zaak(LocalDate overtredingsdatum, String pleeglocatie, Persoon persoon, List<Feit> feiten) {
        this(overtredingsdatum, pleeglocatie);
        this.persoon = persoon;
        this.feiten = feiten;
    }

    public Zaak(LocalDate overtredingsdatum, String pleeglocatie, Persoon persoon, List<Feit> feiten, List<ZaakStatus> zaakstatus) {
        this(overtredingsdatum, pleeglocatie);
        this.persoon = persoon;
        this.feiten = feiten;
        this.zaakstatus = zaakstatus;
    }

    public Zaak(Integer zaaknr, LocalDate overtredingsdatum, String pleeglocatie, Persoon persoon, List<Feit> feiten, List<ZaakStatus> zaakstatus) {
        this(overtredingsdatum, pleeglocatie, persoon, feiten, zaakstatus);
        this.zaaknr = zaaknr;
    }

    public List<ZaakStatus> getZaakstatus() {
        return zaakstatus;
    }

    public void setZaakstatus(List<ZaakStatus> zaakStatus) {
        this.zaakstatus = zaakStatus;
    }

    public int getZaaknr() {
        return zaaknr;
    }

    public void setZaaknr(int zaakNr) {
        this.zaaknr = zaakNr;
    }

    public LocalDate getOvertredingsdatum() {
        return overtredingsdatum;
    }

    public void setOvertredingsdatum(LocalDate overtredingsDatum) {
        this.overtredingsdatum = overtredingsDatum;
    }

    public String getPleeglocatie() {
        return pleeglocatie;
    }

    public void setPleeglocatie(String pleegLocatie) {
        this.pleeglocatie = pleegLocatie;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    public List<Feit> getFeiten() {
        return feiten;
    }

    public void setFeiten(List<Feit> feiten) {
        this.feiten = feiten;
    }

    @Override
    public String toString() {
        return "Zaak{" + "zaaknr=" + zaaknr + ", overtredingsdatum=" + overtredingsdatum + ", pleeglocatie='" + pleeglocatie + '\'' + ", persoon=" + persoon + ", feiten=" + feiten + ", zaakStatus="
                + zaakstatus + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Zaak zaak = (Zaak) o;
        return zaaknr == zaak.zaaknr && Objects.equals(overtredingsdatum, zaak.overtredingsdatum) && Objects.equals(pleeglocatie, zaak.pleeglocatie) && Objects.equals(persoon, zaak.persoon) && Objects
                .equals(feiten, zaak.feiten) && Objects.equals(zaakstatus, zaak.zaakstatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zaaknr, overtredingsdatum, pleeglocatie, persoon, feiten, zaakstatus);
    }
}
