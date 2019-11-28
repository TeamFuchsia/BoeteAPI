package nl.fuchsia.controller;

import nl.fuchsia.model.Feit;
import nl.fuchsia.services.FeitService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/feiten")
public class FeitContoller {
    private final FeitService feitService;

    public FeitContoller(FeitService feitService) {
        this.feitService = feitService;
    }

    /**
     * Deze methode vangt de geposte json @param feit op. Vervolgens haalt hij een nieuw feitnummer op en set deze in de @param feit.
     * Dit feit word daarna doorgezet naar de service, zodat deze in de database(list) kan worden opgeslagen.
     */
    @PostMapping
    public void addFeit(@Valid @RequestBody Feit feit) {
        feitService.addFeit(feit);
    }
}
