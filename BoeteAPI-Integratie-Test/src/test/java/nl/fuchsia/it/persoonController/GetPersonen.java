package nl.fuchsia.it.persoonController;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import cucumber.api.java.nl.Als;
import cucumber.api.java.nl.Dan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;

public class GetPersonen {
	/**
	 * IT voor het opvragen van personen
	 */
	@CitrusResource
	private TestRunner runner;

	@CitrusEndpoint
	private HttpClient boeteApiClient;

	@Autowired
	private DataSource dataSource;

	@Als("de client een GET request maakt naar {string}")
	public void callUrl(String url) {
		runner.http(
			httpActionBuilder -> httpActionBuilder
				.client(boeteApiClient)
				.send()
				.get(url)
		);
	}

	@Dan("moet de HTTP status code {int} zijn en moeten {int} elementen in de response zitten")
	public void verifyResponse(int httpStatusCode, int numberOfElements) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.receive()
			.response(HttpStatus.valueOf(httpStatusCode))
			.validate("$.payload.length()", String.valueOf(numberOfElements))
		);
	}
}
