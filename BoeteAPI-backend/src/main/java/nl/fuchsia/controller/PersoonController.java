package nl.fuchsia.controller;

import nl.fuchsia.dto.Payload;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.services.PersoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/personen")
public class PersoonController {

    private PersoonService persoonService;

    @Autowired
    public PersoonController(PersoonService persoonService) {
        this.persoonService = persoonService;
    }

    /**
     * Wordt gebruikt om te checken of er records in de List staan via de persoonService.
     *
     * @return - Roept de methode getPersonen aan in persoonService.
     */
    @GetMapping
    public ResponseEntity<Payload<Persoon>> getPersonen() {
        Payload<Persoon> payload = new Payload<>(persoonService.getPersonen());
        return ResponseEntity.ok(payload);
    }

    /**
     * Valideert de ingevoerde persoon op basis van een Json object en voegt deze persoon toe via de persoonService.
     *
     * @param persoon - De toe te voegen persoons gegevens.
     * @return - Geeft de toegevoegde persoon terug in het formaat van een Json object.
     */
    @PostMapping
    public ResponseEntity<Persoon> addPersoon(@Valid @RequestBody Persoon persoon) {

        return ResponseEntity.ok(persoonService.addPersoon(persoon));
    }

    /**
     * verkrijgt  één persoon op bassis van de meegegeven ID nummer.
     *
     * @return
     */
    @GetMapping(value = "/{persoonnr}")
    public ResponseEntity<Persoon> getPersoonById(@PathVariable("persoonnr") Integer persoonnr) {

        return ResponseEntity.ok(persoonService.getPersoonById(persoonnr));
    }

    /**
     * Wijzigt de persoon op bassis van de meegegeven ID nummer in Json object.
     */
    @PutMapping(value = "/{persoonnr}")
    public ResponseEntity<Persoon> updatePersoonById(@Valid @PathVariable("persoonnr") Integer persoonnr, @RequestBody Persoon persoon) {

        return ResponseEntity.ok(persoonService.updatePersoonById(persoonnr, persoon));
    }
}
