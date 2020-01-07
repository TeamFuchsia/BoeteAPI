package nl.fuchsia.controller;

import java.util.List;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.model.ZaakStatus;
import nl.fuchsia.services.ZaakService;
import nl.fuchsia.services.ZaakStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/statussen")
public class ZaakStatusController {

    private final ZaakStatusService zaakStatusService;

    @Autowired
    public ZaakStatusController(ZaakStatusService zaakStatusService) {
        this.zaakStatusService = zaakStatusService;
    }

    @PostMapping
    public ResponseEntity<ZaakStatus> addStatusZaak(@Valid @RequestBody ZaakStatus zaakStatus) {
        return ResponseEntity.ok(zaakStatusService.addZaakStatus(zaakStatus));
    }

    @GetMapping
    public ResponseEntity<List<ZaakStatus>> getZaakStatussen() {
        return ResponseEntity.ok(zaakStatusService.getZaakStatussen());
    }
}