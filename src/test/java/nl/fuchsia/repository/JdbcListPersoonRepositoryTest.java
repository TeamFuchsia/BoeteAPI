package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import nl.fuchsia.repository.historie.JdbcPersoonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class JdbcListPersoonRepositoryTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    JdbcPersoonRepository jdbcPersoonRepository;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getJdbcPersonen() {

        List<Persoon> personen = jdbcPersoonRepository.getJdbcPersonen();

        assertThat(personen).isEmpty();
        verify(jdbcTemplate, times(1)).query(anyString(), isA(RowMapper.class));
    }
}