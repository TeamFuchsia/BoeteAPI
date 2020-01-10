package nl.fuchsia.services;

import java.util.ArrayList;
import java.util.List;

import nl.fuchsia.dto.ZaakDto;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.model.ZaakStatus;
import org.springframework.stereotype.Service;

@Service
public class ZaakDtoService {


    public void SetFeitnrsDto(Zaak zaak, ZaakDto zaakDto) {
        zaakDto.setOvertredingsdatum(zaak.getOvertredingsdatum());
        zaakDto.setPleeglocatie(zaak.getPleeglocatie());
        zaakDto.setPersoonnr(zaak.getPersoon().getPersoonnr());

        List<Integer> feitnrs = new ArrayList<>();
        for (Feit feiten : zaak.getFeiten()) {
            int dtoFeitnr = feiten.getFeitNr();
            feitnrs.add(dtoFeitnr);
        }
        zaakDto.setFeitnrs(feitnrs);

        SetZaakStatusDto(zaakDto, zaak);
    }

    public List<ZaakDto> setZakenDtos(List<Zaak> zaken) {
        List<ZaakDto> zaakDtos = new ArrayList<>();

        for (Zaak zaak : zaken) {
            ZaakDto dtoZaken = new ZaakDto();
            dtoZaken.setZaaknr(zaak.getZaaknr());

            SetFeitnrsDto(zaak, dtoZaken);

            zaakDtos.add(dtoZaken);
        }
        return zaakDtos;
    }

    public ZaakDto SetZaakStatusDto(ZaakDto zaakDto, Zaak zaak) {
        List<Integer> zaakStatusnrs = new ArrayList<>();
        for (ZaakStatus zaakStatusNr : zaak.getZaakStatus()) {
            int dtoZaakStatusnr = zaakStatusNr.getZaakstatusnr();
            zaakStatusnrs.add(dtoZaakStatusnr);
        }
        zaakDto.setZaakstatusnr(zaakStatusnrs);

        zaakDto.setZaaknr(zaak.getZaaknr());
        return zaakDto;
    }

    public ZaakDto SetZaakDto(Zaak zaak) {
        ZaakDto zaakDto = new ZaakDto();
        SetFeitnrsDto(zaak, zaakDto);
        return zaakDto;
    }
}
