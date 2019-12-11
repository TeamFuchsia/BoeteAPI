package nl.fuchsia.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;

import static org.mockito.MockitoAnnotations.initMocks;

public class ORMFeitRepositoryTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    ORMFeitRepository ormFeitRepository;


    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void addFeit() {
    }

    @Test
    public void getFeiten() {
    }
}