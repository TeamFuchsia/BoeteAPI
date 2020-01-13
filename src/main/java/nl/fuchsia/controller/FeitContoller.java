package nl.fuchsia.controller;

import javax.validation.Valid;

import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Payload;
import nl.fuchsia.services.FeitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feiten")
public class FeitContoller {
    private final FeitService feitService;

    public FeitContoller(FeitService feitService) {
        this.feitService = feitService;
    }

    /**
     * Deze methode voegt een feit aan de database toe
     *
     * @param feit wordt uit een Json post opgehaald
     *             Dit feit word daarna doorgezet naar de service
     */

    // Daarnaast vangt de Valid annotatie de valliditeit op van de velden van class Feit
    @PostMapping
    public ResponseEntity<Feit> addFeit(@Valid @RequestBody Feit feit) {
        return ResponseEntity.ok(feitService.addFeit(feit));
    }

    /**
     * Haalt alle Feiten uit de database
     * @return een lijst met alle feiten
     */
    @GetMapping
    public ResponseEntity<Payload<Feit>> getFeiten() {
        Payload<Feit> payload = new Payload<>(feitService.getFeiten());
        return ResponseEntity.ok().body(payload);
    }

    /**
     * Wijzigt het feit op bassis van de meegegeven ID nummer in Json object.
     */
    @PutMapping
    public ResponseEntity<Feit> updateFeitById(@Valid @RequestBody Feit feit) {

        return ResponseEntity.ok(feitService.updateFeitById(feit));
    }
}