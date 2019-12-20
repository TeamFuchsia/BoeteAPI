package nl.fuchsia.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.fuchsia.util.JsonDate;

/**
 * DTO voor het wijzigen van een persoon.
 */
//DTO is toegevoegd om de validatie op persoonr te kunnen uitvoeren.
public class PersoonEditDto {
    @NotNull(message = ("Persoonr moet ingevuld zijn"))
    private Integer persoonnr;
    @NotBlank(message = ("Voornaam moet ingevuld zijn"))
    private String voornaam;
    @NotBlank(message = ("Achternaam moet ingevuld zijn"))
    private String achternaam;
    @NotBlank(message = ("Straat moet ingevuld zijn"))
    private String straat;
    @NotBlank(message = ("Huisnummer moet ingevuld zijn"))
    private String huisnummer;
    @NotBlank(message = ("Postcode moet ingevuld zijn"))
    @Pattern(regexp = "\\d{4}\\s[A-Z]{2}$", message = ("Voer een geldige postcode in. 4 cijfers, een spatie en 2 hoofdletters"))
    private String postcode;
    @NotBlank(message = ("Woonplaats moet ingevuld zijn"))
    private String woonplaats;
    @Pattern(regexp = "\\d{9}$", message = ("Voer een geldig 9 cijferig BSN nummer in."))
    private String bsn;
    @JsonProperty("geboortedatum")
    @JsonDate
    private LocalDate geboortedatum;

    public PersoonEditDto() {
    }

    public PersoonEditDto(String voornaam, String achternaam, String straat, String huisnummer, String postcode, String woonplaats, String bsn, LocalDate geboortedatum) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.bsn = bsn;
        this.geboortedatum = geboortedatum;
    }

    public PersoonEditDto(Integer persoonnr, String voornaam, String achternaam, String straat, String huisnummer, String postcode, String woonplaats, String bsn, LocalDate geboortedatum) {
        this(voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum);
        this.persoonnr = persoonnr;
    }

    public Integer getPersoonnr() {
        return persoonnr;
    }

    public void setPersoonnr(Integer persoonnr) {
        this.persoonnr = persoonnr;
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

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }
}