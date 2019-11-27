package nl.fuchsia.controller;


import nl.fuchsia.model.Persoon;
import nl.fuchsia.services.PersoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/personen")
public class PersoonController {

    private PersoonService persoonService;

    @Autowired
    public PersoonController(PersoonService persoonService) {
        this.persoonService = persoonService;
    }

    @GetMapping
    public ResponseEntity<List<Persoon>> personen() {
        return ResponseEntity.ok(persoonService.getPersonen());
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Persoon> addPersoon (@RequestBody Persoon persoon){
        return ResponseEntity.ok(persoonService.addPersoon());
    }
}
