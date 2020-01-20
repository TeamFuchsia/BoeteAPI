package nl.fuchsia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.fuchsia.util.JsonDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * De binnenkomende zaakDto in Json formaat.
 */
public class ZaakDto {

    private int zaaknr;

    @JsonProperty("overtredingsDatum")
    @NotNull(message = ("Overtredingsdatum dient te zijn gevuld!"))
    @JsonDate
    private LocalDate overtredingsdatum;

    @Size(max = 100, message = "Meer dan 100 tekens in pleeglocatie! Pleeglocatie mag maximaal 100 tekens bevatten")
    private String pleeglocatie;

    private int persoonnr;

    @NotNull(message = "Voeg minimaal 1 feit toe.")
    @Size(min = 1, message = "Voeg minimaal 1 feit toe.")
    private List<Integer> feitnrs;

    private List<Integer> zaakstatusnr;

    public ZaakDto() {
    }

    public ZaakDto(int zaaknr, LocalDate overtredingsdatum, String pleeglocatie, int persoonnr, List<Integer> feitnrs) {
        this.zaaknr = zaaknr;
        this.overtredingsdatum = overtredingsdatum;
        this.pleeglocatie = pleeglocatie;
        this.persoonnr = persoonnr;
        this.feitnrs = feitnrs;
    }

    public ZaakDto(int zaaknr, LocalDate overtredingsdatum, String pleeglocatie, int persoonnr, List<Integer> feitnrs, List<Integer> zaakstatusnr) {
        this(zaaknr, overtredingsdatum, pleeglocatie, persoonnr, feitnrs);
        this.zaakstatusnr = zaakstatusnr;
    }

    public int getZaaknr() {
        return zaaknr;
    }

    public void setZaaknr(int zaaknr) {
        this.zaaknr = zaaknr;
    }

    public LocalDate getOvertredingsdatum() {
        return overtredingsdatum;
    }

    public void setOvertredingsdatum(LocalDate overtredingsdatum) {
        this.overtredingsdatum = overtredingsdatum;
    }

    public String getPleeglocatie() {
        return pleeglocatie;
    }

    public void setPleeglocatie(String pleeglocatie) {
        this.pleeglocatie = pleeglocatie;
    }

    public int getPersoonnr() {
        return persoonnr;
    }

    public void setPersoonnr(int persoonnr) {
        this.persoonnr = persoonnr;
    }

    public List<Integer> getFeitnrs() {
        return feitnrs;
    }

    public void setFeitnrs(List<Integer> feitnrs) {
        this.feitnrs = feitnrs;
    }

    public List<Integer> getZaakstatusnr() {
        return zaakstatusnr;
    }

    public void setZaakstatusnr(List<Integer> zaakstatusnr) {
        this.zaakstatusnr = zaakstatusnr;
    }
}
