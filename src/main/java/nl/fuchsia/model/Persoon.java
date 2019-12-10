package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "persoon", schema = "public")
public class Persoon {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer persoonnr;
    @Column
    @NotBlank(message = ("Voornaam moet ingevuld zijn"))
    private String voornaam;
    @Column
    @NotBlank(message = ("Achternaam moet ingevuld zijn"))
    private String achternaam;
    @Column
    @NotBlank(message = ("Straat moet ingevuld zijn"))
    private String straat;
    @Column
    @NotBlank(message = ("Huisnummer moet ingevuld zijn"))
    private String huisnummer;
    @Column
    @NotBlank(message = ("Postcode moet ingevuld zijn"))
    @Pattern(regexp = "\\d\\d\\d\\d\\s[A-Z][A-Z]", message = ("Voer een geldige postcode in. 4 cijfers, een spatie en 2 hoofdletters"))
    private String postcode;
    @Column
    @NotBlank(message = ("Woonplaats moet ingevuld zijn"))
    private String woonplaats;
    @Column
    @Pattern(regexp = "\\d\\d\\d\\d\\d\\d\\d\\d\\d", message = ("Voer een geldig 9 cijferig BSN nummer in."))
    private String Bsn;
    @Column
    @JsonProperty("geboortedatum")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate geboortedatum;

    public Persoon() {
    }

    public Persoon(Integer persoonnr) {
        this.persoonnr = persoonnr;
    }

    public Persoon(Integer persoonnr, String voornaam, String achternaam, String straat, String huisnummer, String postcode, String woonplaats, String bsn, LocalDate geboortedatum) {
        this.persoonnr = persoonnr;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.Bsn = bsn;
        this.geboortedatum = geboortedatum;
    }

    public Integer getPersoonnr() {
        return persoonnr;
    }

    public void setPersoonnr(Integer persoonId) {
        this.persoonnr = persoonId;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getBsn() {
        return Bsn;
    }

    void setBsn(String bsn) {
        Bsn = bsn;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persoon persoon = (Persoon) o;
        return getPersoonnr().equals(persoon.getPersoonnr()) &&
                Objects.equals(getVoornaam(), persoon.getVoornaam()) &&
                Objects.equals(getAchternaam(), persoon.getAchternaam()) &&
                Objects.equals(getStraat(), persoon.getStraat()) &&
                Objects.equals(getHuisnummer(), persoon.getHuisnummer()) &&
                Objects.equals(getPostcode(), persoon.getPostcode()) &&
                Objects.equals(getWoonplaats(), persoon.getWoonplaats()) &&
                Objects.equals(getBsn(), persoon.getBsn()) &&
                Objects.equals(getGeboortedatum(), persoon.getGeboortedatum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersoonnr(), getVoornaam(), getAchternaam(), getStraat(), getHuisnummer(), getPostcode(), getWoonplaats(), getBsn(), getGeboortedatum());
    }
}
