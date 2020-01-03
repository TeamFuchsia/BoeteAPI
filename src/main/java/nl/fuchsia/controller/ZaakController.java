package nl.fuchsia.controller;

import java.util.List;
import javax.validation.Valid;

import nl.fuchsia.dto.ZaakAddDto;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.services.ZaakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zaken")

public class ZaakController {

    private final ZaakService zaakService;

    @Autowired
    public ZaakController(ZaakService zaakService) {
        this.zaakService = zaakService;
    }

    @PostMapping
    public ResponseEntity<Zaak> addZaak(@Valid @RequestBody ZaakAddDto zaakAddDto) {
        return ResponseEntity.ok(zaakService.addZaak(zaakAddDto));
    }

    @GetMapping()
    public ResponseEntity<List<Zaak>> getZaken(@RequestParam(value = "persoonnr", required = false) Integer persoonnr) {
        List<Zaak> zaken;

        if (persoonnr != null) {
            zaken = zaakService.getZakenByPersoon(persoonnr);
        } else {
            zaken = zaakService.getZaken();
        }

        return ResponseEntity.ok().body(zaken);
    }

    @GetMapping(value = "/{zaakNr}")
    public ResponseEntity getZaakById(@PathVariable("zaakNr") Integer zaakNr) {
        return ResponseEntity.ok(zaakService.getZaakById(zaakNr));
    }
}