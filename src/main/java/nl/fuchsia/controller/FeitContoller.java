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

    @PostMapping
    public void addFeit(@Valid @RequestBody Feit feit) {

        feit.setFeitNr(feitService.getNieuwFeitnummer());
        feitService.addFeit(feit);
    }
}
