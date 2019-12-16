package nl.fuchsia.dto;

import java.time.LocalDate;
import java.util.List;

public class ZaakDto {

    private int zaaknr;

    private LocalDate overtredingsDatum;

    private String pleegLocatie;

    private int persoonnr;

    private List<Integer> feitnrs;

}
