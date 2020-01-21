package nl.fuchsia.it.persoonController;

import javax.sql.DataSource;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import cucumber.api.java.nl.Als;
import cucumber.api.java.nl.Gegeven;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

public class AddPersoon {
    /**
     * IT voor het toevoegen van personen
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Autowired
    private DataSource dataSource;

    @Gegeven("er zit {int} personen in de database.")
    public void generateRandomPeople(int nrOfPeople) {
        runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement("DELETE FROM persoon"));
        createRandomPersonenAdd(nrOfPeople);
    }

    @Als("de client een POST maakt naar {string} met")
    public void deClientEenPOSTMaaktNaarMet(String url, String body) {
        runner.http(httpActionBuilder -> httpActionBuilder.client(boeteApiClient).send().post(url).messageType(MessageType.JSON).contentType(ContentType.APPLICATION_JSON.getMimeType()).payload(body));
    }

    private void createRandomPersonenAdd(int nrOfPeople) {
        for (int i = 0; i < nrOfPeople; i++) {
            String bsn = String.valueOf(899999999 + i);
            int huisnummer = 50 + i;
            runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement(
                    "INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) " + "VALUES ('Karel', 'Derf', 'Fredstraat', " + huisnummer
                            + ", '1234 KL', 'Groningen', " + bsn + ", '01-01-2000')"));
        }
    }
}