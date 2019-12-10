package nl.fuchsia.repository;
import nl.fuchsia.model.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.sql.*;
import java.util.Objects;

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
    // Voegt personen toe aan de localdatabase BoeteAPI
    // Persoon ID hoeft niet gemaakt, deze wordt gegenereerd door de Database.
    public void addPersoon(Persoon persoon) {
        String SQL = "INSERT INTO Persoon(voornaam,achternaam,straat,huisnummer,postcode,woonplaats,geboortedatum,bsn) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {


            pstmt.setString(1, persoon.getVoornaam());
            pstmt.setString(2, persoon.getAchternaam());
            pstmt.setString(3, persoon.getStraat());
            pstmt.setString(4, persoon.getHuisnummer());
            pstmt.setString(5, persoon.getPostcode());
            pstmt.setString(6, persoon.getWoonplaats());
            pstmt.setObject(7, persoon.getGeboortedatum());
            pstmt.setString(8, persoon.getBsn());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
