package nl.fuchsia.it.persoonController;

import java.util.Random;
import javax.sql.DataSource;

import com.consol.citrus.actions.ExecuteSQLQueryAction;
import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.config.CitrusSpringConfig;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import cucumber.api.java.nl.Als;
import cucumber.api.java.nl.Gegeven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CitrusSpringConfig.class)
public class GetPersoonById {
    /**
     * IT voor het opvragen van een persoon op persoonnr.
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Autowired
    private DataSource dataSource;

    private String persoonnr;

    @Gegeven("er zitten {int} personen in de database")
    public void generateRandomPeople(int nrOfPeople) {
        runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement("DELETE FROM persoon"));
        createRandomPersonen(nrOfPeople);
        ExecuteSQLQueryAction query = runner.query(action -> action.dataSource(dataSource).statement("SELECT PERSOONNR FROM public.persoon LIMIT 1").extract("PERSOONNR", "selectPersoonnr"));
    persoonnr = runner.variable("selectPersoonnr", "${selectPersoonnr}");
}

    @Als("de client een GETbyID request maakt naar {string} met persoonnr")
    public void callUrl(String url) {
        String urlPersoonnr = url.concat(persoonnr);
        runner.http(httpActionBuilder -> httpActionBuilder.client(boeteApiClient).send().get(urlPersoonnr));
    }

    @Als("de client een GETbyID request maakt naar {string}")
    public void callInvallidPersoonnr(String url) {
        runner.http(httpActionBuilder -> httpActionBuilder.client(boeteApiClient).send().get(url));
    }

    private void createRandomPersonen(int nrOfPeople) {
        Random random = new Random();
        for (int i = 0; i < nrOfPeople; i++) {
            String bsn = String.valueOf(random.nextInt(899999999) + 100000000);
            int huisnummer = 50 + i;
            runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement(
                    "INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) " + "VALUES ('Gerard', 'Derf', 'Fredstraat', " + huisnummer
                            + ", '1234 KL', 'Groningen', " + bsn + ", '01-01-2000')"));
        }
    }
}