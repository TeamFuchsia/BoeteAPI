package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.fuchsia.util.JsonDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "zaak")
public class Zaak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int zaakNr;

    @JsonProperty("overtredingsDatum")
    @NotNull(message = ("Overtredingsdatum dient te zijn gevuld!"))
    @Column
    @JsonDate
    private LocalDate overtredingsDatum;

    @Size(max = 100, message = "Meer dan 100 tekens in pleeglocatie! Pleeglocatie mag maximaal 100 tekens bevatten")
    // mag leeg zijn indien het adminstratieve boete is bijv boete niet verzekerd.
    private String pleegLocatie;

    @ManyToOne
    @JoinColumn(name = "persoonnr")
    @NotBlank
    private Persoon persoon;

    @ManyToMany
    @JoinTable(name = "zaakregel",
            joinColumns =
            @JoinColumn(name = "zaaknr", referencedColumnName = "zaaknr"),
            inverseJoinColumns =
            @JoinColumn(name = "feitnr", referencedColumnName = "feitnr"))
    @NotBlank
    private List<Feit> feiten;

    public Zaak() {
    }

    public Zaak(LocalDate overtredingsDatum, String pleegLocatie, Persoon persoon, List<Feit> feiten) {
        this.overtredingsDatum = overtredingsDatum;
        this.pleegLocatie = pleegLocatie;
        this.persoon = persoon;
        this.feiten = feiten;
    }

    public Zaak(int zaakNr, LocalDate overtredingsDatum, String pleegLocatie, Persoon persoon, List<Feit> feiten) {
        this.zaakNr = zaakNr;
        this.overtredingsDatum = overtredingsDatum;
        this.pleegLocatie = pleegLocatie;
        this.persoon = persoon;
        this.feiten = feiten;
    }

    public int getZaakNr() {
        return zaakNr;
    }

    public void setZaakNr(int zaakNr) {
        this.zaakNr = zaakNr;
    }

    public LocalDate getOvertredingsDatum() {
        return overtredingsDatum;
    }

    public void setOvertredingsDatum(LocalDate overtredingsDatum) {
        this.overtredingsDatum = overtredingsDatum;
    }

    public String getPleegLocatie() {
        return pleegLocatie;
    }

    public void setPleegLocatie(String pleegLocatie) {
        this.pleegLocatie = pleegLocatie;
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
        return "Zaak{" +
                "zaakNr=" + zaakNr +
                ", overtredingsDatum=" + overtredingsDatum +
                ", pleegLocatie='" + pleegLocatie + '\'' +
                ", persoon=" + persoon +
                ", feiten=" + feiten +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zaak zaak = (Zaak) o;
        return zaakNr == zaak.zaakNr &&
                Objects.equals(overtredingsDatum, zaak.overtredingsDatum) &&
                Objects.equals(pleegLocatie, zaak.pleegLocatie) &&
                Objects.equals(persoon, zaak.persoon) &&
                Objects.equals(feiten, zaak.feiten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zaakNr, overtredingsDatum, pleegLocatie, persoon, feiten);
    }
}