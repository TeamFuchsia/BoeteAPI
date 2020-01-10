package nl.fuchsia.controller;

import java.util.List;
import javax.validation.Valid;

import nl.fuchsia.dto.ZaakDto;
import nl.fuchsia.dto.ZaakAddStatusDto;
import nl.fuchsia.dto.ZaakAddFeitDto;
import nl.fuchsia.model.Payload;
import nl.fuchsia.model.Zaak;
import nl.fuchsia.services.ZaakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zaken")
public class ZaakController {

	private final ZaakService zaakService;

	@Autowired
	public ZaakController(ZaakService zaakService) {
		this.zaakService = zaakService;
	}

    @PostMapping
    public ResponseEntity<ZaakDto> addZaak(@Valid @RequestBody ZaakDto zaakDto) {
        return ResponseEntity.ok(zaakService.addZaak(zaakDto));
    }

    @PostMapping (value = "/{zaakNr}/statussen")
    public ResponseEntity<ZaakDto> addZaakStatus(@PathVariable("zaakNr") Integer zaakNr, @Valid @RequestBody ZaakAddStatusDto zaakAddStatusDto) {
        return ResponseEntity.ok(zaakService.updZaakStatus(zaakNr, zaakAddStatusDto));
    }

    @GetMapping
    public ResponseEntity<Payload<ZaakDto>> getZaken(@RequestParam(value = "persoonnr", required = false) Integer persoonnr) {
        Payload<ZaakDto> payload;

		if (persoonnr != null) {
			payload = new Payload<>(zaakService.getZakenByPersoon(persoonnr));
		} else {
			payload = new Payload<>(zaakService.getZaken());
		}

		return ResponseEntity.ok().body(payload);
	}

    @GetMapping(value = "/{zaakNr}")
    public ResponseEntity<ZaakDto> getZaakById(@PathVariable("zaakNr") Integer zaakNr) {
        return ResponseEntity.ok(zaakService.getZaakById(zaakNr));
    }

    @PostMapping(value = "/{zaakNr}/feiten")
    public ResponseEntity<ZaakDto> updZaakFeit(@PathVariable("zaakNr") Integer zaakNr, @RequestBody List<ZaakAddFeitDto> listZaakAddFeitDto) {
        return ResponseEntity.ok(zaakService.updZaakFeit(zaakNr, listZaakAddFeitDto));
    }
}
