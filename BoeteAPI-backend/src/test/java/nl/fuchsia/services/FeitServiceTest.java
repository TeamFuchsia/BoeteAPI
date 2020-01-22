package nl.fuchsia.services;

import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.FeitRepository;
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

public class FeitServiceTest {
    @Mock
    private FeitRepository feitRepository;
    @InjectMocks
    private FeitService feitService;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testAddFeit() {
        Feit feit = new Feit();

        feitService.addFeit(feit);

        verify(feitRepository).addFeit(feit);
    }

    @Test
    public void testNonUniekFeitcodeExeption() {
        when(feitRepository.addFeit(any(Feit.class))).thenThrow(new TransactionSystemException("TestException"));

        assertThatThrownBy(() -> feitService.addFeit(new Feit(1, "VBF-001", "Test", 500))).isInstanceOf(UniekVeldException.class).hasMessage("Feitcode: VBF-001 bestaat al in de database.");
    }

    @Test
    public void testGetFeiten() {
        feitService.getFeiten();

        verify(feitRepository).getFeiten();
    }

    /**
     * Test of de methode updateFeitByID in de feitRepository wordt aangeroepen.
     */
    @Test
    public void testUpdateFeitById() {
        Feit feit = new Feit("VBF-004", "Test", 500);
        int feitnr = 10;

        when(feitRepository.getFeitById(feitnr)).thenReturn(feit);

        feitService.updateFeitById(feitnr, feit);

        verify(feitRepository).updateFeitById(feit);
    }

    /**
     * Test controleert of het BSN bij het updaten/toevoegen al bestaat in de database.
//     */
    @Test
    public void testFeitcodeUpdateExeption() {
        Feit feit = new Feit("VBF-003", "Test", 500);
        int feitnr = 3;
        when(feitRepository.getFeitById(feitnr)).thenReturn(feit);

        assertThatThrownBy(() -> feitService.updateFeitById(feitnr, new Feit( feitnr,"VBF-004", "Test", 500))).isInstanceOf(UniekVeldException.class)
                .hasMessage("Feitcode: VBF-003 mag niet gewijzigd worden in VBF-004");
    }

    /**
     * Test controleert of de te updaten persoonnr bestaat.
     */
    @Test
    public void testBestaanPersoonnr() {
        when(feitRepository.updateFeitById(any(Feit.class))).thenThrow(new TransactionSystemException("TestException"));

        assertThatThrownBy(() -> feitService.updateFeitById(3, new Feit(3,"VBF-003", "Test", 500)))
                .isInstanceOf(NotFoundException.class).hasMessage("Feitnummer: 3 bestaat niet!");
    }
}