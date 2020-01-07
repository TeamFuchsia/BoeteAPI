package nl.fuchsia.it;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.config.CitrusSpringConfig;
import com.consol.citrus.dsl.builder.HttpClientActionBuilder;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestRunner;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.jdbc.server.JdbcServer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;

@ContextConfiguration(classes = CitrusSpringConfig.class)
public class ExampleSteps extends JUnit4CitrusTestRunner {

    /**
     * Test runner filled with actions by step definitions
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    //    @CitrusEndpoint
//    private JdbcServer jdbcServer;
//
    @Autowired
    private DataSource dataSource;

    private HttpClientActionBuilder.HttpClientReceiveActionBuilder response;

    @Given("^the user is authenticated with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void authenticate(String user, String password) {
        System.out.println(user);
        System.out.println(password);
        // authenticate
    }

    @When("^the client makes a GET request to \"([^\"]*)\"$")
    public void callUrl(String url) {
        runner.http(
                httpActionBuilder -> httpActionBuilder
                        .client(boeteApiClient)
                        .send()
                        .get(url)
        );
    }

    @Then("^the HTTP status code should be (\\d+)$")
    public void expectedHttpStatus(int status) {
        runner.http(
                httpActionBuilder -> httpActionBuilder
                        .client(boeteApiClient)
                        .receive()
                        .response(HttpStatus.valueOf(status))
        );
    }

    @Then("^the content should be valid$")
    public void verifyContent() {
        runner.http(
                httpActionBuilder -> httpActionBuilder
                        .client(boeteApiClient)
                        .receive()
                        .response()
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .payload("{}")
        );
    }

    @Given("there are {int} people in the database")
    public void generateRandomPeople(int nrOfPeople) {
        createRandomPersoon();
    }

    @Then("the HTTP status code should be {int} and the result should contain {int} elements")
    public void theHTTPStatusCodeShouldBeAndTheResultShouldContainElements(int arg0, int arg1) {
    }

    private void createRandomPersoon() {
        this.run(plsql(sqlBuilder -> {
            sqlBuilder
                    .dataSource(dataSource)
                    .statement("INSERT INTO public.persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) VALUES ('Fred', 'Derf', 'Fredstraat', '10', '1234 KL', 'Groningen', '123456789', '01-01-2000')");
        }));
    }
}
