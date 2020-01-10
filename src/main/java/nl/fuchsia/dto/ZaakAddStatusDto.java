package nl.fuchsia.dto;

import javax.validation.constraints.NotNull;

public class ZaakAddStatusDto {

    @NotNull(message = ("Statusnummer dient te zijn ingevuld!"))
    private int statusnr;

    public int getStatusNr() {
        return statusnr;
    }

    public void setStatusNr(int statusnr) {
        this.statusnr = statusnr;
    }

    public ZaakAddStatusDto() {
    }

    public ZaakAddStatusDto(int statusnr) {
        this.statusnr = statusnr;
    }
}