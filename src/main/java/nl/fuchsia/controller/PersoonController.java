package nl.fuchsia.controller;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.services.PersoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    // Hoort niet in de user story 1-RH.
//    @GetMapping
//    public ResponseEntity<List<Persoon>> getPersonen() {
//        return ResponseEntity.ok(persoonService.getPersonen());}

    /**
     * Valideert de ingevoerde persoon op basis van een Json object en voegt deze persoon toe via de persoonService.
     *
     * @param persoon - De toe te voegen persoons gegevens.
     * @return - Geeft de toegevoegde persoon terug in het formaat van een Json object.
     */

    @PostMapping
    public ResponseEntity<Persoon> addPersoon(@Valid @RequestBody Persoon persoon) {
        persoonService.addPersoon(persoon);
        return ResponseEntity.ok(persoon);
    }

//    @PostMapping
//    public Persoon addPersoon(@Valid @RequestBody Persoon persoon) {
//        return persoonService.addPersoon(persoon);
//    }

    @GetMapping(value = "/{persoonnr}")
    public Persoon getPersoonById(@PathVariable("persoonnr") Integer persoonnr) {
        return persoonService.getPersoonById(persoonnr);
    }
    }