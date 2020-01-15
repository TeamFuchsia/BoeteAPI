package nl.fuchsia.it.FeitController;

import javax.sql.DataSource;

import com.consol.citrus.annotations.*;
import com.consol.citrus.config.CitrusSpringConfig;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import cucumber.api.java.nl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CitrusSpringConfig.class)
public class GetFeiten {

    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Autowired
    private DataSource dataSource;

    @Gegeven("er zitten {int} feiten in de database")
    public void generateRandomFeiten(int nrOfFeiten) {
        runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement("DELETE FROM feit"));
        createRandomFeiten(nrOfFeiten);
    }

    @Als("de client een GET request maakt naar {string}")
    public void deClientEenGETRequestMaaktNaar(String url) {
        runner.http(httpActionBuilder -> httpActionBuilder.client(boeteApiClient).send().get(url));
    }

    @Dan("moet de HTTP status code {int} zijn en moeten er {int} elementen in de response zitten")
    public void moetDeHTTPStatusCodeZijnEnMoetenErElementenInDeResponseZitten(int httpStatusCode, int numberOfElements) {
            runner.http(httpActionBuilder -> httpActionBuilder
                    .client(boeteApiClient)
                    .receive()
                    .response(HttpStatus.valueOf(httpStatusCode))
                    .validate("$.payload.length()", String.valueOf(numberOfElements))
            );
    }

    private void createRandomFeiten(int nrOfFeiten) {
        for (int i = 0; i < nrOfFeiten; i++) {
            String feitcode = "VBF-00" + (i + 1);
            runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement("INSERT INTO feit (feitcode, omschrijving, bedrag) VALUES ('" + feitcode + "', 'Test overtreding', 20)"));
        }
    }
}