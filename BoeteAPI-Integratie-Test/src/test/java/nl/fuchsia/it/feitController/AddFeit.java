package nl.fuchsia.it.feitController;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import cucumber.api.java.nl.Als;
import cucumber.api.java.nl.Dan;
import cucumber.api.java.nl.Gegeven;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;

public class AddFeit {

    /**
     * Test runner filled with actions by step definitions
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Autowired
    private DataSource dataSource;

    @Gegeven("er zit {int} feiten in de database.")
    public void genereateRadomFeiten(int nrOfFeiten) {
        runner.plsql(sqlBuilder -> sqlBuilder
                .dataSource(dataSource)
                .statement("DELETE FROM feit")
        );
        createNewFeiten(nrOfFeiten);
    }

    private void createNewFeiten(int nrOfFeiten) {
        for (int i = 0; i < nrOfFeiten; i++) {
            String feitcode = "'VBF-01" + i + "'";
            String omschrijving = "'" + String.valueOf(5 + i) + " km/u te hard gereden'";
            double bedrag = 150 + i * 100;

            runner.plsql(sqlBuilder -> sqlBuilder
                    .dataSource(dataSource)
                    .statement("INSERT INTO feit (feitcode, omschrijving, bedrag) " +
                            "VALUES (" + feitcode + ", " + omschrijving + ", " + bedrag + ")"));
        }
    }

    @Als("de client een nieuwe feit toevoegt via {string}")
    public void addCorrectNewFeit(String url) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .send()
                .post(url)
                .messageType(MessageType.JSON)
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .payload("{ \"feitcode\":\"VBF-200\",\"omschrijving\":\"U rijdt een Dacia Logan Sedan beige uitvoering\",\"bedrag\": 450 }"));
    }

    @Als("de client een nieuwe feit toevoegt met een bestaande feitcode via {string}")
    public void AddWrongNewFeit(String url) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .send()
                .post(url)
                .messageType(MessageType.JSON)
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .payload("{ \"feitcode\":\"VBF-010\",\"omschrijving\":\"U rijdt een Dacia Lodgy\",\"bedrag\": 500 }"));
    }

    @Als("de client een nieuwe feit toevoegt zonder omschrijving via {string}")
    public void AddEmptyNewFeit(String url) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .send()
                .post(url)
                .messageType(MessageType.JSON)
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .payload("{ \"feitcode\":\"VBF-100\",\"omschrijving\":\"\",\"bedrag\": 200 }"));
    }

    @Dan("moet de HTTP status code {int} zijn en feitcode moet zijn {string}")
    public void verifyResponse(int httpStatusCode, String waardeFeitCode) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .receive()
                .response(HttpStatus.valueOf(httpStatusCode))
                .validate("$.feitcode", String.valueOf(waardeFeitCode)));
    }

    @Dan("moet de HTTP status code {int} zijn met error response {string}")
    public void verifyErrorResponse(int httpStatusCode, String errorResponse) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .receive()
                .response(HttpStatus.valueOf(httpStatusCode))
                .validate("$.error", String.valueOf(errorResponse))
        );
    }

    @Dan("moet de HTTP status code {int} zijn met error response: {string}")
    public void verifyEmptyErrorResponse(int httpStatusCode, String errorResponse) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .receive()
                .response(HttpStatus.valueOf(httpStatusCode))
                .validate("$.error", String.valueOf(errorResponse))
        );
    }
}