package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Repository
public class JdbcPersoonRepository {

    private static final String GET_PERSOON_BY_NR = "SELECT * FROM PERSOON WHERE persoonnr = ?";

    private JdbcTemplate jdbcOperations;

    @Autowired
    public JdbcPersoonRepository(JdbcTemplate jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public Persoon getByPersoonNr(Integer persoonnr) {
        return jdbcOperations.queryForObject(GET_PERSOON_BY_NR, this::rowMapper, persoonnr);
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
