package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class Zaak {

private int zaakNr;

    @JsonProperty("overtredingsDatum")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    //ToDo datum formatter (wordt opgepakt door Sander)
    private LocalDate overtredingsDatum;


    @Size(max = 100, message = "Meer dan 100 tekens in pleeglocatie! Pleeglocatie mag maximaal 100 tekens bevatten")
    // mag leeg zijn indien het adminstratieve boete is bijv boete niet verzekerd.
    private String pleegLocatie;


    public Zaak() {
    }

    public Zaak(int zaakNr, LocalDate overtredingsDatum, String pleegLocatie) {
        this.zaakNr = zaakNr;
        this.overtredingsDatum = overtredingsDatum;
        this.pleegLocatie = pleegLocatie;
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
