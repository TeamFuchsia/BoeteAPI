package nl.fuchsia.it.persoonController;

import javax.sql.DataSource;

import com.consol.citrus.actions.ExecuteSQLQueryAction;
import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import cucumber.api.java.nl.Als;
import cucumber.api.java.nl.Gegeven;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

public class UpdatePersoonById {
    /**
     * IT voor het wijzigen van een persoon.
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Autowired
    private DataSource dataSource;

    private String persoonnr;

    @Gegeven("er zit {int} persoon in de database.")
    public void generateRandomPeople(int nrOfPeople) {
        runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement("DELETE FROM persoon"));
        createRandomPersonenAdd(nrOfPeople);
        ExecuteSQLQueryAction query = runner.query(action -> action.dataSource(dataSource).statement("SELECT PERSOONNR FROM public.persoon LIMIT 1").extract("PERSOONNR", "selectPersoonnr"));
        persoonnr = runner.variable("selectPersoonnr", "${selectPersoonnr}");
    }

    private void createRandomPersonenAdd(int nrOfPeople) {
        for (int i = 0; i < nrOfPeople; i++) {
            String bsn = String.valueOf(899999999 + i);
            int huisnummer = 50 + i;
            runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement(
                    "INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) " + "VALUES ('Karel', 'Derf', 'Fredstraat', " + huisnummer
                            + ", '1234 KL', 'Groningen', " + bsn + ", '15-01-2000')"));
        }
    }

    @Als("de client een PutbyID request maakt naar {string} met persoonnr")
    public void updateByIdUrl(String url, String body) {
        String urlPersoonnr = url.concat(persoonnr);
        runner.http(httpActionBuilder -> httpActionBuilder.client(boeteApiClient).send().put(urlPersoonnr).messageType(MessageType.JSON).contentType(ContentType.APPLICATION_JSON.getMimeType())
                .payload(body));
    }

    @Als("de client een PutbyID request maakt naar {string}")
    public void updateInvalidIdByIdUrl(String url, String body) {
        runner.http(httpActionBuilder -> httpActionBuilder.client(boeteApiClient).send().put(url).messageType(MessageType.JSON).contentType(ContentType.APPLICATION_JSON.getMimeType()).payload(body));
    }
}