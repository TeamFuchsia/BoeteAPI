package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.assertj.core.api.ListAssert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

//import static org.junit.Assert.*;
import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcPersoonRepositoryTest {


    DataSource dataSource;
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    JdbcPersoonRepository jdbcPersoonRepository = new JdbcPersoonRepository(jdbcTemplate);

    @Test
    public void getJdbcPersonen() {

//        List<Persoon> jdbcPersonen = jdbcPersoonRepository.getJdbcPersonen();
//        jdbcPersonen.get(0).getVoornaam();
//        assertThat(jdbcPersoonRepository.getJdbcPersonen().get(0).getVoornaam()).contains("Rense");

    }
}