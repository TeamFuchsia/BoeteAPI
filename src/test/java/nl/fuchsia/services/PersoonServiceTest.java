package nl.fuchsia.services;

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
    public void setUp() throws Exception {
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

    @Test
    public void testNonUniekBsnExeption() {
        when(persoonRepository.addPersoon(any(Persoon.class))).thenThrow(new TransactionSystemException("TestException"));

        assertThatThrownBy(() -> persoonService.addPersoon(new Persoon(1, "Rense", "Houwing", "De buren", "10", "8402 GH", "Drachten", "123456789", LocalDate.of(1990, 10, 12))))
                .isInstanceOf(UniekVeldException.class).hasMessage("BSN nummer: 123456789 bestaat reeds.");
    }
}