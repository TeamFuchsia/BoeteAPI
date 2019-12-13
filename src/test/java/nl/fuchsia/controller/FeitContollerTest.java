package nl.fuchsia.controller;

import nl.fuchsia.model.Feit;
import nl.fuchsia.services.FeitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class FeitContollerTest {
    @Mock
    FeitService feitService;
    @InjectMocks
    FeitContoller feitContoller;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testAddFeit() {
        Feit feit = new Feit();

        feitContoller.addFeit(feit);

        verify(feitService).addFeit(feit);
    }

    // TODO wat nou als ik een invalid feit aan de controller aanbiedt? Spring controller tests.
}