/*
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.fuchsia.it;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.builder.HttpClientActionBuilder;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.jdbc.server.JdbcServer;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.fuchsia.model.Persoon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.sql.DataSource;

/**
 * designer
 *
 * @author Christoph Deppisch
 */
public class ExampleSteps extends TestNGCitrusTestRunner {

    /**
     * Test runner filled with actions by step definitions
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @CitrusEndpoint
    private JdbcServer jdbcServer;

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
