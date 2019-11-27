package nl.fuchsia.model;

import javax.validation.constraints.*;

public class Feit {


    private long feitNr;

    @NotNull(message = "Feitcode mist, voeg deze nog toe")
    @Pattern(regexp = "VBF-\\d\\d\\d$", message = "Feitcode moet voldoen aan de standaard opmaak, VBF- gevolgd door 3 cijfers, bv VBF-000")
    private String feitcode;

    @NotNull(message = "Omschrijving mist, voeg deze nog toe")
    @Size(max = 5000, message = "Omschrijving mag niet meer dan 5000 tekens bevatten")
    private String omschrijving;

    @NotNull(message = "Bedrag mist, voeg deze nog toe")
    @DecimalMin(value = "0.01", message = "Bedrag mag niet 0.00 of negatief zijn")
    @Digits(integer = 10, fraction = 2, message = "Bedrag moet 2 decimalen bevatten en kleiner dan 100.000.000.000")
    private double bedrag;


    public Feit() {
    }

    public Feit(long feitNr, String feitcode, String omschrijving, double bedrag) {
        this.feitNr = feitNr;
        this.feitcode = feitcode;
        this.omschrijving = omschrijving;
        this.bedrag = bedrag;
    }

    public String getFeitcode() {
        return feitcode;
    }

    public void setFeitcode(String feitcode) {
        this.feitcode = feitcode;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public long getFeitNr() {
        return feitNr;
    }

    public void setFeitNr(long feitNr) {
        this.feitNr = feitNr;
    }

    @Override
    public String toString() {
        return "Feit{" +
                "feitNr=" + feitNr +
                ", feitcode='" + feitcode + '\'' +
                ", omschrijving='" + omschrijving + '\'' +
                ", bedrag=" + bedrag +
                '}';
    }
}
