package nl.fuchsia.dto;

import javax.validation.constraints.Min;

public class ZaakAddStatusDto {

    @Min(value = 1,message = ("Statusnummer dient groter dan 0 te zijn"))
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