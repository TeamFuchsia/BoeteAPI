package nl.fuchsia.dto;

public class ZaakAddFeitDto {

    private int feitNr;

    public ZaakAddFeitDto() {
    }

    public ZaakAddFeitDto(int feitNr) {
        this.feitNr = feitNr;
    }

    public int getFeitNr() {
        return feitNr;
    }

    public void setFeitNr(int feitNr) {
        this.feitNr = feitNr;
    }
}
