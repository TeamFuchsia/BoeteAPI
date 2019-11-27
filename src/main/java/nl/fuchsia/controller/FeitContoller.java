package nl.fuchsia.controller;

import nl.fuchsia.model.Feit;
import nl.fuchsia.services.FeitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/feiten")
public class FeitContoller {

    private final FeitService feitService;

    public FeitContoller(FeitService feitService) {
        this.feitService = feitService;
    }

    @PostMapping
    public void addFeit(@Valid @RequestBody Feit feit) {

        feit.setFeitNr(feitService.getNieuwFeitnummer());
        feitService.addFeit(feit);
    }

    @GetMapping
    public ResponseEntity<List<Feit>> getFeiten() {

        return ResponseEntity
                .ok()
                .body(feitService.getFeiten());
    }
}
