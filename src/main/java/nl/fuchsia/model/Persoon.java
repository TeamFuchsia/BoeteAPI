package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;

public class Persoon {

    private Integer persoonId;
    @NotEmpty(message = ("Voornaam moet ingevuld zijn"))
    private String voornaam;
    @NotEmpty(message = ("Achternaam moet ingevuld zijn"))
    private String achternaam;
    @NotEmpty(message = ("Straat moet ingevuld zijn"))
    private String straat;
    @NotEmpty(message = ("Huisnummer moet ingevuld zijn"))
    private String huisnummer;
    @NotEmpty(message = ("Postcode moet ingevuld zijn"))
    @Pattern(regexp = "\\d\\d\\d\\d\\s[A-Z][A-Z]", message = ("Voer een geldige postcode in. 4 cijfers, een spatie en 2 hoofdletters"))
    private String postcode;
    @NotEmpty(message = ("Woonplaats moet ingevuld zijn"))
    private String woonplaats;
    @Pattern(regexp = "\\d\\d\\d\\d\\d\\d\\d\\d\\d", message = ("Voer een geldig 9 cijferig BSN nummer in."))
    private String Bsn;
    //   @Past(message = "Geboortedatum moet in het verleden liggen.")
    //    @Pattern(regexp = "^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"
    //    , message = ("Voor een geldige datum in: dd-mm-jjjj"))
    @JsonProperty("geboortedatum")
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
        return getPersoonId().equals(persoon.getPersoonId()) &&
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
        return Objects.hash(getPersoonId(), getVoornaam(), getAchternaam(), getStraat(), getHuisnummer(), getPostcode(), getWoonplaats(), getBsn(), getGeboortedatum());
    }
}
