package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

public class Persoon {

    private Integer persoonId;
    private String voornaam;
    private String achternaam;
    private String straat;
    private String huisnummer;
    @Pattern(regexp = "\\d\\d\\d\\d\\s[A-Z][A-Z]")
    private String postcode;
    private String woonplaats;
    @Pattern(regexp = "\\d\\d\\d\\d\\d\\d\\d\\d\\d")
    private String Bsn;
    //@JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    //@JsonFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate geboortedatum;

    public Persoon() {
    }

    public Persoon(Integer persoonId) {
        this.persoonId = persoonId;
    }

    public Persoon(Integer persoonId, String voornaam, String achternaam, String straat, String huisnummer, String postcode, String woonplaats, String bsn, LocalDate geboortedatum) {
        this.persoonId = persoonId;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.Bsn = bsn;
        this.geboortedatum = geboortedatum;
    }

    public Integer getPersoonId() {
        return persoonId;
    }

    public void setPersoonId(Integer persoonId) {
        this.persoonId = persoonId;
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
        return Bsn;
    }

    public void setBsn(String bsn) {
        Bsn = bsn;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }
}
