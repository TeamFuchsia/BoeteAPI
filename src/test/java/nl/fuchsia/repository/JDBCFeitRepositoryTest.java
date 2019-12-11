package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.junit.Before;
import org.junit.Test;
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

// Depricated
public class JDBCFeitRepositoryTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    JDBCFeitRepository jdbcFeitRepository;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetFeiten() {

        List<Feit> feiten = jdbcFeitRepository.getFeiten();

        assertThat(feiten).isEmpty();
        verify(jdbcTemplate, times(1)).query(anyString(), isA(RowMapper.class));
    }
}