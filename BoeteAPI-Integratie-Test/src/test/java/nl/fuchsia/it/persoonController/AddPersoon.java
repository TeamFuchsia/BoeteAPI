package nl.fuchsia.it.persoonController;

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
import java.util.Random;

public class AddPersoon {
	/**
	 * Test runner filled with actions by step definitions
	 */
	@CitrusResource
	private TestRunner runner;

	@CitrusEndpoint
	private HttpClient boeteApiClient;

	@Autowired
	private DataSource dataSource;

	private String persoonnr;

	@Gegeven("er zit {int} personen in de database.")
	public void generateRandomPeople(int nrOfPeople) {
		runner.plsql(sqlBuilder -> sqlBuilder
			.dataSource(dataSource)
			.statement("DELETE FROM persoon")
		);
		createRandomPersonenAdd(nrOfPeople);
	}

	@Als("de client een nieuwe persoon toevoegt via {string}")
	public void callUrl(String url) {
		TestAction testing = runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.send()
			.post(url)
			.messageType(MessageType.JSON)
			.contentType(ContentType.APPLICATION_JSON.getMimeType())
			.payload("{ \"voornaam\": \"Hans\", \"achternaam\": \"Anders\", \"straat\": \"Kerkstraat\"," +
				" \"huisnummer\": \"88\", \"postcode\": \"2201 EB\", \"woonplaats\": \"Leeuwarden\", \"bsn\": \"777654111\"," +
				" \"geboortedatum\": \"02-12-1958\"}")
		);
		System.out.println("Hallo");
	}

	@Dan("moet de HTTP status code {int} zijn en huisnummer moet {int} zijn in de response.")
	public void verifyResponse(int httpStatusCode, int numberOfElements) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.receive()
			.response(HttpStatus.valueOf(httpStatusCode))
			.validate("$.huisnummer", String.valueOf(numberOfElements))
		);
	}

	private void createRandomPersonenAdd(int nrOfPeople) {
		Random random = new Random();
		for (int i = 0; i < nrOfPeople; i++) {
			String bsn = String.valueOf(random.nextInt(899999999) + 100000000);
			int huisnummer = 50 + i;
			runner.plsql(sqlBuilder -> sqlBuilder
				.dataSource(dataSource)
				.statement("INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) " +
					"VALUES ('Karel', 'Derf', 'Fredstraat', " + huisnummer + ", '1234 KL', 'Groningen', " + bsn + ", '01-01-2000')"));
		}
	}
}
