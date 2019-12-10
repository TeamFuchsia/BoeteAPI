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

    /**
     * Deze methode voegt een feit aan de database toe
     * @param feit wordt uit een Json post opgehaald
     * Dit feit word daarna doorgezet naar de service
     */

    // Daarnaast vangt de Valid annotatie de valliditeit op van de velden van class Feit

    @PostMapping
    public ResponseEntity<Feit> addFeit(@Valid @RequestBody Feit feit) {
        feitService.addFeit(feit);
        return ResponseEntity.ok(feit);
    }

    @GetMapping
    public ResponseEntity<List<Feit>> getFeiten(){
        return ResponseEntity.ok().body(feitService.getFeiten());
    }
}
