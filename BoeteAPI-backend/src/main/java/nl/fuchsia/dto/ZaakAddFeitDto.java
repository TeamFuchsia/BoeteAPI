package nl.fuchsia.dto;

public class ZaakAddFeitDto {

    private int feitnr;

    public ZaakAddFeitDto() {
    }

    public ZaakAddFeitDto(int feitnr) {
        this.feitnr = feitnr;
    }

    public int getFeitNr() {
        return feitnr;
    }

    public void setFeitNr(int feitnr) {
        this.feitnr = feitnr;
    }
}
