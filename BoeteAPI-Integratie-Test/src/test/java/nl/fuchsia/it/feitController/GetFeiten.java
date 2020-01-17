package nl.fuchsia.it.feitController;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import cucumber.api.java.nl.Als;
import cucumber.api.java.nl.Dan;
import cucumber.api.java.nl.Gegeven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;

public class GetFeiten {

    /**
     * Test runner filled with actions by step definitions
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Autowired
    private DataSource dataSource;

    @Gegeven("er zitten {int} feiten in de database.")
    public void genereateRandomGetFeiten(int nrOfGetFeiten) {
        runner.plsql(sqlBuilder -> sqlBuilder
                .dataSource(dataSource)
                .statement("DELETE FROM feit")
        );
        createGetFeiten(nrOfGetFeiten);
    }

    private void createGetFeiten(int nrOfGetFeiten) {
        for (int i = 0; i < nrOfGetFeiten; i++) {
            String feitcode = "'VBF-11" + i + "'";
            String omschrijving = "'" + String.valueOf(5 + i) + " km/u te hard gereden'";
            double bedrag = 150 + i * 100;

            runner.plsql(sqlBuilder -> sqlBuilder
                    .dataSource(dataSource)
                    .statement("INSERT INTO feit (feitcode, omschrijving, bedrag) " +
                            "VALUES (" + feitcode + ", " + omschrijving + ", " + bedrag + ")"));
        }
    }

    @Als("de client vraagt alle feiten op via {string}")
    public void callGetUrl(String url) {
        runner.http(
                httpActionBuilder -> httpActionBuilder
                        .client(boeteApiClient)
                        .send()
                        .get(url)
        );
    }

    @Dan("moet de HTTP status code {int} de Response dient {int} feiten te bevatten")
    public void verifyResponse(int httpStatusCode, int numberOfElements) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .receive()
                .response(HttpStatus.valueOf(httpStatusCode))
                .validate("$.payload.length()", String.valueOf(numberOfElements))
        );
    }
}
