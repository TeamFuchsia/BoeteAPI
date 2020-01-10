package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class ZaakStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer zaakstatusnr;

    @Column
    @JsonProperty("veranderdatum")
    private LocalDate veranderdatum;

    @ManyToOne
    @JoinColumn(name = "statusnr")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "zaaknr")
    private Zaak zaak;

    public ZaakStatus() {
    }

    public ZaakStatus(LocalDate veranderdatum, Status status, Zaak zaak) {
        this.veranderdatum = veranderdatum;
        this.status = status;
        this.zaak = zaak;
    }

    public ZaakStatus (LocalDate veranderdatum, Status status) {
        this.veranderdatum = veranderdatum;
        this.status = status;
    }

	public ZaakStatus(Integer zaakstatusnr, LocalDate veranderdatum, Status status, Zaak zaak) {
		this(veranderdatum,status,zaak);
    	this.zaakstatusnr = zaakstatusnr;
	}

    public Integer getZaakstatusnr() {
        return zaakstatusnr;
    }

    public void setZaakstatusnr(Integer zaakstatusnr) {
        this.zaakstatusnr = zaakstatusnr;
    }

    public LocalDate getVeranderdatum() {
        return veranderdatum;
    }

    public void setVeranderdatum(LocalDate veranderdatum) {
        this.veranderdatum = veranderdatum;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonIgnore
    public Zaak getZaak() {
        return zaak;
    }

    public void setZaak(Zaak zaak) {
        this.zaak = zaak;
    }

    @Override
    public String toString() {
        return "ZaakStatus{" +
                "zaakstatusnr=" + zaakstatusnr +
                ", veranderdatum=" + veranderdatum +
                ", status=" + status +
                ", zaak=" + zaak +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZaakStatus that = (ZaakStatus) o;
        return Objects.equals(zaakstatusnr, that.zaakstatusnr) &&
                Objects.equals(veranderdatum, that.veranderdatum) &&
                Objects.equals(status, that.status) &&
                Objects.equals(zaak, that.zaak);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zaakstatusnr, veranderdatum, status, zaak);
    }
}