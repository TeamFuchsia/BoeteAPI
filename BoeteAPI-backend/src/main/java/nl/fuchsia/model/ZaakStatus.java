package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.naming.Name;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "zaakstatus")
public class ZaakStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zaakstatusnr")
    private Integer zaakStatusnr;

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

    public ZaakStatus(Integer zaakStatusnr, LocalDate veranderdatum, Status status, Zaak zaak) {
        this(veranderdatum, status, zaak);
        this.zaakStatusnr = zaakStatusnr;
    }

    public Integer getZaakStatusnr() {
        return zaakStatusnr;
    }

    public void setZaakStatusnr(Integer zaakstatusnr) {
        this.zaakStatusnr = zaakstatusnr;
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

    public int getZaak() {
        return zaak.getZaaknr();
    }

    public void setZaak(Zaak zaak) {
        this.zaak = zaak;
    }

    @Override
    public String toString() {
        return "ZaakStatus{" + "zaakstatusnr=" + zaakStatusnr + ", veranderdatum=" + veranderdatum + ", status=" + status + ", zaak=" + zaak.getZaaknr() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ZaakStatus that = (ZaakStatus) o;
        return Objects.equals(zaakStatusnr, that.zaakStatusnr) && Objects.equals(veranderdatum, that.veranderdatum) && Objects.equals(status, that.status) && Objects
                .equals(zaak.getZaaknr(), that.zaak.getZaaknr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(zaakStatusnr, veranderdatum, status, zaak);
    }
}
