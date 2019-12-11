package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;

import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

public class OrmPersoonRepositoryTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    OrmPersoonRepository ormPersoonRepository;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getOrmPersonen() {

//        List<Persoon> ormPersonen = ormPersoonRepository.getOrmPersonen();
//
//        assertThat(ormPersonen).isEmpty();
//        verify(entityManager, times(1)).query(anyString(), isA(RowMapper.class));
    }
}