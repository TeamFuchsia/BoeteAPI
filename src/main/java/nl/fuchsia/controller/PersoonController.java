package nl.fuchsia.controller;

import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.services.PersoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public ResponseEntity<List<Persoon>> getPersonen() {
        return ResponseEntity.ok(persoonService.getPersonen());
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

    @GetMapping(value = "/{persoonnr}")
    public Persoon getPersoonById(@PathVariable("persoonnr") Integer persoonnr) {
        return persoonService.getPersoonById(persoonnr);
    }

    @PutMapping(value = "/{persoonnr}")
    public ResponseEntity<Persoon>  updatePersoonById(@Valid@RequestBody Persoon persoon,@PathVariable("persoonnr") Integer persoonnr){

        if (!Objects.equals(persoonnr,persoon.getPersoonnr())){
            throw new UniekVeldException("PersoonId: <"  + persoonnr + "> bestaat niet en/of komt niet overeen!");}

        return ResponseEntity.ok(persoonService.updatePersoonById(persoon));

    }

//    @PutMapping("/{persoonnr}")
//    public ResponseEntity<Persoon> updateEmployee(@PathVariable(value = "persoonnr") Integer persoonNr,
//                                                   @Valid @RequestBody Persoon persoonDetails) throws UniekVeldException {
//        Persoon persoon = persoonService.getPersoonById(persoonNr);
//
//        persoon.setPersoonnr(persoonDetails.getPersoonnr());
//        persoon.setVoornaam(persoonDetails.getVoornaam());
//        persoon.setAchternaam(persoonDetails.getAchternaam());
//        persoon.setStraat(persoonDetails.getStraat());
//        persoon.setHuisnummer(persoonDetails.getHuisnummer());
//        persoon.setPostcode(persoonDetails.getPostcode());
//        persoon.setWoonplaats(persoonDetails.getWoonplaats());
//        persoon.setBsn(persoonDetails.getBsn());
//        persoon.setGeboortedatum(persoonDetails.getGeboortedatum());
//
//
//
//        final Persoon updatedPersoon = persoonService.updatePersoonById(persoon);
//        return ResponseEntity.ok(updatedPersoon);
//}
   }