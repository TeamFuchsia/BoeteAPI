package nl.fuchsia.it.persoonController;

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
import java.util.Random;

//@ContextConfiguration(classes = CitrusSpringConfig.class)
public class GetPersoonById {
	/**
	 * Test runner filled with actions by step definitions
	 */
	@CitrusResource
	private TestRunner runner;

	@CitrusEndpoint
	private HttpClient boeteApiClient;

	@Autowired
	private DataSource dataSource;

	@Gegeven("er zitten {int} personen in de database.")
	public void generateRandomPeople(int nrOfPeople) {
		runner.plsql(sqlBuilder -> sqlBuilder
			.dataSource(dataSource)
			.statement("DELETE FROM persoon")
		);
		createRandomPersonen(nrOfPeople);
	}

	@Als("de client een GETbyID request maakt naar {string}")
	public void callUrl(String url) {
		runner.http(
			httpActionBuilder -> httpActionBuilder
				.client(boeteApiClient)
				.send()
				.get(url)
		);
	}

	@Dan("moet de HTTP status code {int} zijn en moet er {int} element in de response zitten")
	public void verifyResponse(int httpStatusCode, int numberOfElements) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.receive()
			.response(HttpStatus.valueOf(httpStatusCode))
			.validate("$.length()", String.valueOf(numberOfElements))
		);
	}


	private void createRandomPersonen(int nrOfPeople) {
		Random random = new Random();
		for (int i = 0; i < nrOfPeople; i++) {
			String bsn = String.valueOf(random.nextInt(899999999) + 100000000);
			runner.plsql(sqlBuilder -> sqlBuilder
				.dataSource(dataSource)
				.statement("INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) VALUES ('Fred', 'Derf', 'Fredstraat', '10', '1234 KL', 'Groningen', " + bsn + ", '01-01-2000')"));
		}
	}
}
