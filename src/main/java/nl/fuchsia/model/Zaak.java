package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message = ("Overtredingsdatum dient te zijn gevuld!"))
    @Column
    //ToDo datum formatter (wordt opgepakt door Sander)
    private LocalDate overtredingsDatum;

    @Size(max = 100, message = "Meer dan 100 tekens in pleeglocatie! Pleeglocatie mag maximaal 100 tekens bevatten")
    // mag leeg zijn indien het adminstratieve boete is bijv boete niet verzekerd.
    private String pleegLocatie;

    @ManyToMany
    @JoinTable(name = "zaakregel",
            joinColumns =
            @JoinColumn(name = "zaaknr", referencedColumnName = "zaaknr"),
            inverseJoinColumns =
            @JoinColumn(name = "feitnr", referencedColumnName = "feitnr"))
    private List<Feit> feiten;

    public Zaak() {
    }

    public Zaak(LocalDate overtredingsDatum, String pleegLocatie, List<Feit> feiten) {
        this.overtredingsDatum = overtredingsDatum;
        this.pleegLocatie = pleegLocatie;
        this.feiten = feiten;
    }

    public Zaak(int zaakNr, LocalDate overtredingsDatum, String pleegLocatie, List<Feit> feiten) {
        this.zaakNr = zaakNr;
        this.overtredingsDatum = overtredingsDatum;
        this.pleegLocatie = pleegLocatie;
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

    @Override
    public String toString() {
        return "Zaak{" +
                "zaakNr=" + zaakNr +
                ", overtredingsDatum=" + overtredingsDatum +
                ", pleegLocatie='" + pleegLocatie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zaak zaak = (Zaak) o;
        return getZaakNr() == zaak.getZaakNr() &&
                Objects.equals(getOvertredingsDatum(), zaak.getOvertredingsDatum()) &&
                Objects.equals(getPleegLocatie(), zaak.getPleegLocatie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZaakNr(), getOvertredingsDatum(), getPleegLocatie());
    }
}