package nl.fuchsia.it.feitController;

import com.consol.citrus.TestAction;
import com.consol.citrus.actions.ExecuteSQLQueryAction;
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

public class UpdateFeitById {

    /**
     * Test runner filled with actions by step definitions
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Autowired
    private DataSource dataSource;


    @Gegeven("er zitten {int} feiten t.b.v. het updaten van een feit in de database.")
    public void genereateRadomFeiten(int nrOfFeiten) {
        runner.plsql(sqlBuilder -> sqlBuilder
                .dataSource(dataSource)
                .statement("DELETE FROM feit")
        );
        createNewFeiten(nrOfFeiten);
        ExecuteSQLQueryAction query = runner.query(action -> action
                .dataSource(dataSource)
                .statement("SELECT FEITNR FROM public.feit LIMIT 1")
                .extract("FEITNR", "selectFeitnr")
        );
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

    @Als("de client een PUT updateFeitById met een {string} invoer maakt naar {string}")
    public void updateFeit(String invoer, String url) {
        runner.query(action -> action
                .dataSource(dataSource)
                .statement("SELECT * FROM public.feit LIMIT 1")
        );
        String nieweFeitcode = "VBF-999";
        String bestaandeFeitcode = "VBF-010";

        String payload = "";
        switch(invoer){
            case "goede":
                payload = ("{ \"feitnr\": ${selectFeitnr}, \"feitcode\": \"" + nieweFeitcode + "\", \"omschrijving\": \"${OMSCHRIJVING}\", \"bedrag\": ${Bedrag} }");
                break;
            case "geen feitnr":
                payload = ("{ \"feitcode\": \"" + nieweFeitcode + "\", \"omschrijving\": \"${OMSCHRIJVING}\", \"bedrag\": ${Bedrag} }");
                break;
            case "fout feitnr":
                payload = ("{ \"feitnr\": 0, \"feitcode\": \"" + nieweFeitcode + "\", \"omschrijving\": \"${OMSCHRIJVING}\", \"bedrag\": ${Bedrag} }");
                break;
            case "bestaand FeitCode":
                payload = ("{ \"feitnr\": ${selectFeitnr}, \"feitcode\": \"" + bestaandeFeitcode + "\", \"omschrijving\": \"${OMSCHRIJVING}\", \"bedrag\": ${Bedrag} }");
                break;
        }
        String finalPayload = payload;
        TestAction httpPut = runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .send()
                .put(url)
                .messageType(MessageType.JSON)
                .contentType(ContentType.APPLICATION_JSON.getMimeType())
                .payload(finalPayload)
        );
    }

    @Dan("moet de HTTP status code {int} zijn en moet in de response o.a de gewijzigde feitcode {string} zitten")
    public void verifyResponse(int httpStatusCode, String feitcode) {
        runner.http(httpActionBuilder -> httpActionBuilder
                .client(boeteApiClient)
                .receive()
                .response(HttpStatus.valueOf(httpStatusCode))
                .validate("$.feitcode", String.valueOf(feitcode))
        );

    }

}
