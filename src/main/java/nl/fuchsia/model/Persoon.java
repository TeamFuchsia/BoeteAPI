package nl.fuchsia.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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
    @Pattern(regexp = "\\d\\d\\d\\d\\d\\d\\d\\d\\d", message = ("Voor een geldig 9 cijferig BSN nummer in."))
    private String Bsn;
    //   @Past(message = "Geboortedatum moet in het verleden liggen.")
    //    @Pattern(regexp = "^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"
    //    , message = ("Voor een geldige datum in: dd-mm-jjjj"))
    @JsonFormat(pattern = "dd-MM-yyyy")
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

    @Override
    public String toString() {
        return "Persoon{" +
                "persoonId=" + persoonId +
                ", voornaam='" + voornaam + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", straat='" + straat + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", postcode='" + postcode + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                ", Bsn='" + Bsn + '\'' +
                ", geboortedatum=" + geboortedatum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Persoon persoon = (Persoon) o;

        if (getPersoonId() != null ? !getPersoonId().equals(persoon.getPersoonId()) : persoon.getPersoonId() != null)
            return false;
        if (getVoornaam() != null ? !getVoornaam().equals(persoon.getVoornaam()) : persoon.getVoornaam() != null)
            return false;
        if (getAchternaam() != null ? !getAchternaam().equals(persoon.getAchternaam()) : persoon.getAchternaam() != null)
            return false;
        if (getStraat() != null ? !getStraat().equals(persoon.getStraat()) : persoon.getStraat() != null) return false;
        if (getHuisnummer() != null ? !getHuisnummer().equals(persoon.getHuisnummer()) : persoon.getHuisnummer() != null)
            return false;
        if (getPostcode() != null ? !getPostcode().equals(persoon.getPostcode()) : persoon.getPostcode() != null)
            return false;
        if (getWoonplaats() != null ? !getWoonplaats().equals(persoon.getWoonplaats()) : persoon.getWoonplaats() != null)
            return false;
        if (getBsn() != null ? !getBsn().equals(persoon.getBsn()) : persoon.getBsn() != null) return false;
        return getGeboortedatum() != null ? getGeboortedatum().equals(persoon.getGeboortedatum()) : persoon.getGeboortedatum() == null;
    }

    @Override
    public int hashCode() {
        int result = getPersoonId() != null ? getPersoonId().hashCode() : 0;
        result = 31 * result + (getVoornaam() != null ? getVoornaam().hashCode() : 0);
        result = 31 * result + (getAchternaam() != null ? getAchternaam().hashCode() : 0);
        result = 31 * result + (getStraat() != null ? getStraat().hashCode() : 0);
        result = 31 * result + (getHuisnummer() != null ? getHuisnummer().hashCode() : 0);
        result = 31 * result + (getPostcode() != null ? getPostcode().hashCode() : 0);
        result = 31 * result + (getWoonplaats() != null ? getWoonplaats().hashCode() : 0);
        result = 31 * result + (getBsn() != null ? getBsn().hashCode() : 0);
        result = 31 * result + (getGeboortedatum() != null ? getGeboortedatum().hashCode() : 0);
        return result;
    }
}
