package nl.fuchsia.it;

import java.util.Map;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.builder.HttpClientResponseActionBuilder;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import cucumber.api.java.nl.Dan;
import io.cucumber.datatable.DataTable;
import org.springframework.http.HttpStatus;

public class GeneriekIntegratieTest {

    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Dan("moet de HTTP status code {int} zijn en bevat:")
    public void verifyHappyFlow(int httpStatusCode, DataTable table) {
        runner.http(httpActionBuilder -> {
            HttpClientResponseActionBuilder response = httpActionBuilder.client(boeteApiClient).receive().response(HttpStatus.valueOf(httpStatusCode));

            Map<String, String> keyValuePairs = table.asMap(String.class, String.class);

            keyValuePairs.forEach((key, value) -> response.validate("$." + key, value));
        });
    }
}