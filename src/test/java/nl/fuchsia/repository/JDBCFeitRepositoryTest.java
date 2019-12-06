package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class JDBCFeitRepositoryTest {

    @Mock
    DataSource dataSource;

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

        System.out.println(feiten);

        assertThat(feiten).isEmpty();
//        verify(jdbcTemplate.query(Mockito.anyList()), times(1));
    }
}
