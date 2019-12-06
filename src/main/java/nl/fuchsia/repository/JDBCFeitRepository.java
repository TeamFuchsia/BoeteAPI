package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class JDBCFeitRepository {

    private static final String GET_FEITEN =
            "SELECT * FROM feit";

    private JdbcTemplate jdbcTemplate;

    public JDBCFeitRepository() {
    }

    @Autowired
    public JDBCFeitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Feit> getFeiten() {
        return jdbcTemplate.query(GET_FEITEN, this::rowMapper);
    }

    private Feit rowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new Feit(rs.getLong("FEITNR"),
                rs.getString("FEITCODE"),
                rs.getString("OMSCHRIJVING"),
                rs.getDouble("BEDRAG"));
    }
}