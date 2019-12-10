package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrmPersoonRepositoryTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    OrmPersoonRepository ormPersoonRepository;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getOrmPersonen() {

        List<Persoon> ormPersonen = ormPersoonRepository.getOrmPersonen();

        assertThat(ormPersonen).isEmpty();
        //verify(entityManager, times(1)).query(anyString(), isA(RowMapper.class));
    }
}