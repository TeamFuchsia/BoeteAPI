package nl.fuchsia.controller;

import nl.fuchsia.model.ZaakStatus;
import nl.fuchsia.services.ZaakStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/statussen")
public class ZaakStatusController {

    ZaakStatusService zaakStatusService;

    @PostMapping
    public ResponseEntity<ZaakStatus> addStatusZaak(@Valid @RequestBody ZaakStatus zaakStatus) {
        return ResponseEntity.ok(zaakStatusService.addZaakStatus(zaakStatus));
    }
}