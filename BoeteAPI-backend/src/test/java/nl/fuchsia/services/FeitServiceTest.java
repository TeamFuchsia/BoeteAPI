package nl.fuchsia.services;

import nl.fuchsia.exceptionhandlers.NotFoundException;
import nl.fuchsia.exceptionhandlers.UniekVeldException;
import nl.fuchsia.model.Feit;
import nl.fuchsia.repository.FeitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.transaction.TransactionSystemException;

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
        Feit feit = new Feit(2, "VBF-002", "Test", 500);
        when(feitRepository.getFeitById(feit.getFeitNr())).thenReturn(feit);
        Feit updatedfeit = new Feit(2, "VBF-002", "Test", 5000);

        feitService.updateFeitById(updatedfeit);

        verify(feitRepository).getFeitById(feit.getFeitNr());
        verify(feitRepository).updateFeitById(updatedfeit);
    }

    /**
     * Test controleert of het BSN bij het updaten/toevoegen al bestaat in de database.
     */
    @Test
    public void testFeitcodeUpdateExeption() {
        Feit feit = new Feit(3, "VBF-003", "Test", 500);
        when(feitRepository.getFeitById(feit.getFeitNr())).thenReturn(feit);

        assertThatThrownBy(() -> feitService.updateFeitById(new Feit(3, "VBF-004", "Test", 500))).isInstanceOf(UniekVeldException.class)
                .hasMessage("Feitcode: VBF-003 mag niet gewijzigd worden in VBF-004");
    }

    /**
     * Test controleert of de te updaten persoonnr bestaat.
     */
    @Test
    public void testBestaanPersoonnr() {
        when(feitRepository.updateFeitById(any(Feit.class))).thenThrow(new TransactionSystemException("TestException"));

        assertThatThrownBy(() -> feitService.updateFeitById(new Feit(3, "VBF-003", "Test", 500))).isInstanceOf(NotFoundException.class).hasMessage("Feitnummer: 3 bestaat niet!");
    }
}