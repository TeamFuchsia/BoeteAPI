package nl.fuchsia.repository;

import nl.fuchsia.model.Persoon;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class JdbcPersoonRepository {

    public void addPersoon(Persoon persoon) {
        String SQL = "INSERT INTO Persoon(voornaam,achternaam,straat,huisnummer,postcode,woonplaats,geboortedatum,bsn) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/boeteapi/personen");
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, persoon.getPersoonnr());
            pstmt.setString(2, persoon.getVoornaam());
            pstmt.setString(3, persoon.getAchternaam());
            pstmt.setString(4, persoon.getStraat());
            pstmt.setString(5, persoon.getHuisnummer());
            pstmt.setString(6, persoon.getPostcode());
            pstmt.setString(7, persoon.getWoonplaats());
            pstmt.setObject(8, persoon.getGeboortedatum());
            pstmt.setString(9, persoon.getBsn());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}


