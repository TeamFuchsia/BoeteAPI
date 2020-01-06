package nl.fuchsia.controller;

import java.util.List;
import javax.validation.Valid;

import nl.fuchsia.dto.ZaakAddDto;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Payload;
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

    @GetMapping
    public ResponseEntity<Payload<Zaak>> getZaken(@RequestParam(value = "persoonnr", required = false) Integer persoonnr) {
        Payload<Zaak> payload;

        if (persoonnr != null) {
            payload = new Payload<>(zaakService.getZakenByPersoon(persoonnr));
        } else {
            payload = new Payload<>(zaakService.getZaken());
        }

        return ResponseEntity.ok().body(payload);
    }

    @GetMapping(value = "/{zaakNr}")
    public ResponseEntity<Zaak> getZaakById(@PathVariable("zaakNr") Integer zaakNr) {
        return ResponseEntity.ok(zaakService.getZaakById(zaakNr));
    }
}