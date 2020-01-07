package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ZaakStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer zaakstatusnr;

    @Column
    @JsonProperty("veranderdatum")
    private LocalDate veranderdatum;

    //uitzoeken!!!!!!!

    @OneToMany(mappedBy = "statusnr")
    private List<StatusEnum> statusEnum;

    @ManyToOne
    @JoinColumn(name = "zaaknr")
    private Zaak zaak;

    //!!!!!!!!!!!!!!!!!!!!!!!!!


    public ZaakStatus() {
    }

    public ZaakStatus(LocalDate veranderdatum) {
        this.veranderdatum = veranderdatum;
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

    @Override
    public String toString() {
        return "ZaakStatus{" +
                "zaakstatusnr=" + zaakstatusnr +
                ", veranderdatum=" + veranderdatum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZaakStatus that = (ZaakStatus) o;
        return Objects.equals(zaakstatusnr, that.zaakstatusnr) &&
                Objects.equals(veranderdatum, that.veranderdatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zaakstatusnr, veranderdatum);
    }
}