package nl.fuchsia.controller;

import nl.fuchsia.model.Feit;
import nl.fuchsia.services.FeitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class FeitContollerTest {
    @Mock
    FeitService feitService;
    @InjectMocks
    FeitContoller feitContoller;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testAddFeit() {
        Feit feit = new Feit();

        feitContoller.addFeit(feit);

        verify(feitService).addFeit(feit);
    }
}