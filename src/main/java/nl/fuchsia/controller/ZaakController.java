package nl.fuchsia.controller;

import nl.fuchsia.model.Zaak;
import nl.fuchsia.services.ZaakService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/zaken")

public class ZaakController {

    private final ZaakService zaakService;

    public ZaakController(ZaakService zaakService) {
        this.zaakService = zaakService;
    }

    @PostMapping
    public ResponseEntity<Zaak> addZaak(@Valid @RequestBody Zaak zaak) {
        zaakService.addZaak(zaak);
        return ResponseEntity.ok(zaak);
    }

    @GetMapping
    public ResponseEntity<List> getZaken() {
        return ResponseEntity.ok().body(zaakService.getZaken());
    }
}