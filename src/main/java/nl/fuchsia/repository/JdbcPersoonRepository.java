package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcPersoonRepository {

    private static final String GET_PERSONEN = "SELECT * FROM PERSOON";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPersoonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Haalt een lijst van alle personen uit de database m.b.v. jdbc.
     *
     * @return Lijst van personen.
     */
    public List<Persoon> getJdbcPersonen(){
        return jdbcTemplate.query(GET_PERSONEN, this::rowMapper);
    }

    private Persoon rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new Persoon(rs.getInt("persoonnr"),
                rs.getString("voornaam"),
                rs.getString("achternaam"),
                rs.getString("straat"),
                rs.getString("huisnummer"),
                rs.getString("postcode"),
                rs.getString("woonplaats"),
                rs.getString("bsn"),
                rs.getObject("geboortedatum", LocalDate.class));
    }
}
