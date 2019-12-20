package nl.fuchsia.controller;

import nl.fuchsia.dto.ZaakAddDto;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.services.ZaakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<Zaak>> getZaken() {
        return ResponseEntity.ok().body(zaakService.getZaken());
    }

    @GetMapping(value = "/{zaakNr}")
    public Zaak getZaakById(@PathVariable("zaakNr") Integer zaakNr) {
        return zaakService.getZaakById(zaakNr);
    }
}
