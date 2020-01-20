package nl.fuchsia.services;

import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.PersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersoonServiceTest {

    @Mock
    PersoonRepository persoonRepository;
    @InjectMocks
    PersoonService persoonService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    /**
     * Test of de methode addPersoon in de persoonRepository wordt aangeroepen.
     */
    @Test
    public void testAddPersoon() {
        Persoon persoon = new Persoon();

        persoonService.addPersoon(persoon);

        verify(persoonRepository).addPersoon(persoon);
    }

    /**
     * Test of de methode getPersonen in de persoonRepository wordt aangeroepen.
     */
    @Test
    public void testGetPersonen() {
        persoonService.getPersonen();

        verify(persoonRepository).getPersonen();
    }

    /**
     * Test of de methode getPersoonByID in de persoonRepository wordt aangeroepen.
     */
    @Test
    public void testGetPersoonById() {
        Persoon persoon = new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

        when(persoonRepository.getPersoonById(1)).thenReturn(persoon);

        persoonService.getPersoonById(persoon.getPersoonnr());

        verify(persoonRepository).getPersoonById(1);
    }

    /**
     * Test of de methode updatePersoonByID in de persoonRepository wordt aangeroepen.
     */
    @Test
    public void testUpdatePersoonById() {
        Persoon persoon = new Persoon(1, "Henk", "V", "straat", "1", "9999 AA", "Sneek", "123456789", LocalDate.of(1990, 1, 1));

        when(persoonRepository.getPersoonById(1)).thenReturn(persoon);

        persoonService.updatePersoonById(persoon);

        verify(persoonRepository).getPersoonById(1);
        verify(persoonRepository).updatePersoonById(persoon);
    }

    /**
     * Test controleert of het BSN bij het updaten/toevoegen al bestaat in de database.
     */
    @Test
    public void testNonUniekBsnExeptionAddPersoon() {
        when(persoonRepository.addPersoon(any(Persoon.class))).thenThrow(new TransactionSystemException("TestException"));

        assertThatThrownBy(() -> persoonService.addPersoon(new Persoon("Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12))))
                .isInstanceOf(UniekVeldException.class).hasMessage("BSN nummer: 123456789 bestaat reeds.");
    }

    /**
     * Test controleert of de te updaten persoonnr bestaat.
     */
    @Test
    public void testBestaanPersoonnr() {
        when(persoonRepository.updatePersoonById(any(Persoon.class))).thenThrow(new TransactionSystemException("TestException"));

        assertThatThrownBy(() -> persoonService.updatePersoonById(new Persoon(1, "Geert", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12))))
                .isInstanceOf(NotFoundException.class).hasMessage("Persoonnummer: 1 bestaat niet!");
    }

    @Test
    public void testNonUniekBsnExeptionUpdatePersoon() {
        Persoon persoon = new Persoon(1, "Geert", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12));

        when(persoonRepository.updatePersoonById(any(Persoon.class))).thenThrow(new TransactionSystemException("TestException"));
        when(persoonRepository.getPersoonById(persoon.getPersoonnr())).thenReturn(persoon);

        assertThatThrownBy(() -> persoonService.updatePersoonById(persoon)).isInstanceOf(UniekVeldException.class).hasMessage("BSN nummer: 123456789 bestaat reeds.");
    }
}
