package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class ZaakStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer zaakstatusnr;

    @Column
    @JsonProperty("veranderdatum")
    private LocalDate veranderdatum;

    //uitzoeken!!!!!!!

    @ManyToOne
    @JoinColumn(name= "statusnr")
    private int statusnr;

    @ManyToOne
    @JoinColumn(name = "zaaknr")
    private int zaaknr;

    //!!!!!!!!!!!!!!!!!!!!!!!!!

    public ZaakStatus() {
    }

    public ZaakStatus(int statusnr, int zaaknr) {
        this.statusnr = statusnr;
        this.zaaknr = zaaknr;
    }

    public ZaakStatus(LocalDate veranderdatum, int statusnr, int zaaknr) {
        this.veranderdatum = veranderdatum;
        this.statusnr = statusnr;
        this.zaaknr = zaaknr;
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

    public int getStatusnr() {
        return statusnr;
    }

    public void setStatusnr(int statusnr) {
        this.statusnr = statusnr;
    }

    public int getZaaknr() {
        return zaaknr;
    }

    public void setZaaknr(int zaaknr) {
        this.zaaknr = zaaknr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ZaakStatus that = (ZaakStatus) o;
        return statusnr == that.statusnr && zaaknr == that.zaaknr && Objects.equals(zaakstatusnr, that.zaakstatusnr) && Objects.equals(veranderdatum, that.veranderdatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zaakstatusnr, veranderdatum, statusnr, zaaknr);
    }

    @Override
    public String toString() {
        return "ZaakStatus{" + "zaakstatusnr=" + zaakstatusnr + ", veranderdatum=" + veranderdatum + ", statusnr=" + statusnr + ", zaaknr=" + zaaknr + '}';
    }
}