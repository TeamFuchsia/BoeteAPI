package nl.fuchsia.services;

import nl.fuchsia.dto.ZaakDto;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.model.ZaakStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZaakDtoService {

    /**
     * SetZaakDto zet een Zaak om in een ZaakDto
     *
     * @param zaak is de te verwerken zaak
     * @return een ZaakDto met alle velden ingevoerd
     */
    public ZaakDto setZaakDto(Zaak zaak) {
        ZaakDto zaakDto = new ZaakDto();
        zaakDto.setOvertredingsdatum(zaak.getOvertredingsdatum());
        zaakDto.setPleeglocatie(zaak.getPleeglocatie());
        zaakDto.setPersoonnr(zaak.getPersoon().getPersoonnr());

        List<Integer> feitnrs = new ArrayList<>();
        for (Feit feiten : zaak.getFeiten()) {
            int dtoFeitnr = feiten.getFeitnr();
            feitnrs.add(dtoFeitnr);
        }
        zaakDto.setFeitnrs(feitnrs);

        List<Integer> zaakStatusnrs = new ArrayList<>();
        for (ZaakStatus zaakStatusNr : zaak.getZaakstatus()) {

            int dtoZaakStatusnr = zaakStatusNr.getZaakstatusnr();
            zaakStatusnrs.add(dtoZaakStatusnr);
        }
        zaakDto.setZaakstatusnr(zaakStatusnrs);

        zaakDto.setZaaknr(zaak.getZaaknr());

        return zaakDto;
    }
}
