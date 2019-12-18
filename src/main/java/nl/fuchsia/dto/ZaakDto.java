package nl.fuchsia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.fuchsia.util.JsonDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class ZaakDto {

    private int zaaknr;

    @JsonProperty("overtredingsDatum")
    @NotNull(message = ("Overtredingsdatum dient te zijn gevuld!"))
    @JsonDate
    private LocalDate overtredingsDatum;

    @Size(max = 100, message = "Meer dan 100 tekens in pleeglocatie! Pleeglocatie mag maximaal 100 tekens bevatten")
    private String pleegLocatie;

    @NotNull(message = ("Persoonnr dient te zijn gevuld!"))
    private Integer persoonnr;

    @Size(min=1, message = "Voeg minimaal 1 feit toe.")
    private List<Integer> feitnrs;

    public int getZaaknr() {
        return zaaknr;
    }

    public void setZaaknr(int zaaknr) {
        this.zaaknr = zaaknr;
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

    public Integer getPersoonnr() {
        return persoonnr;
    }

    public void setPersoonnr(Integer persoonnr) {
        this.persoonnr = persoonnr;
    }

	public List<Integer> getFeitnrs() {
		return feitnrs;
	}

	public void setFeitnrs(List<Integer> feitnrs) {
		this.feitnrs = feitnrs;
	}
}
