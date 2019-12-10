package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Objects;

@Repository
public class JdbcPersoonRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPersoonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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


