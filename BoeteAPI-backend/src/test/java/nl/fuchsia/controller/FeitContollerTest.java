package nl.fuchsia.controller;

import nl.fuchsia.model.Feit;
import nl.fuchsia.services.FeitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    public void testGetFeiten() {
        feitContoller.getFeiten();

        verify(feitService).getFeiten();
    }

    @Test
    public void testUpdateFeitById() {
        Feit feit = new Feit();
        int feitnr = 1;

        feitContoller.updateFeitById(feitnr, feit);

        verify(feitService).updateFeitById(feitnr, feit);
    }
}
