package nl.fuchsia.services;

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
}