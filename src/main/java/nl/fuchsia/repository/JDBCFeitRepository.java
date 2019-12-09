package nl.fuchsia.repository;

import nl.fuchsia.model.Feit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Objects;

@Repository
public class JDBCFeitRepository {

    private JdbcTemplate jdbcTemplate;

    public JDBCFeitRepository() {
    }

    @Autowired
    public JDBCFeitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFeit(Feit feit){
        String sqlAddFeiten = "INSERT INTO feit(feitcode, omschrijving, bedrag) "
                + "VALUES(?,?,?)";

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlAddFeiten)) {

            pstmt.setString(1, feit.getFeitcode());
            pstmt.setString(2, feit.getOmschrijving());
            pstmt.setDouble(3, feit.getBedrag());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
