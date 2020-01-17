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
            String feitcode = "'VBF-01" + i+"'";
            String omschrijving = "'"+ String.valueOf(5+i)+ " km/u te hard gereden'";
            double bedrag = 150 + i * 100;

            runner.plsql(sqlBuilder -> sqlBuilder
                    .dataSource(dataSource)
                    .statement("INSERT INTO feit (feitcode, omschrijving, bedrag) " +
                                "VALUES (" + feitcode + ", "+ omschrijving + ", " + bedrag +")"));
        }

    }
        @Als("de client een nieuwe feit toevoegt via {string}")
        public void addCorrectNewFeit(String url) {
        double bedrag = 500.0;
            runner.http(httpActionBuilder -> httpActionBuilder
                    .client(boeteApiClient)
                    .send()
                    .post(url)
                    .messageType(MessageType.JSON)
                    .contentType(ContentType.APPLICATION_JSON.getMimeType())
                    .payload("{ \"feitcode\":\"VBF-200\",\"omschrijving\":\"U rijdt een Dacia\",\"bedrag\":"+ bedrag+"}"));
            System.out.println("hello");

    }


    @Dan("moet de HTTP status code {int} zijn en feitcode moet zijn {string}")
    public void verifyResponse(int httpStatusCode, String waardeFeitCode) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .receive()
                .response(HttpStatus.valueOf(httpStatusCode))
               .validate("$.feitcode", String.valueOf(waardeFeitCode))
        );
    }

    }
