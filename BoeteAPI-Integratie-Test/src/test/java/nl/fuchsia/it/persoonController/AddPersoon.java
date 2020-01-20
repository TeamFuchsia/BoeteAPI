package nl.fuchsia.it.persoonController;

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

public class AddPersoon {
	/**
	 * IT voor het toevoegen van personen
	 */
	@CitrusResource
	private TestRunner runner;

	@CitrusEndpoint
	private HttpClient boeteApiClient;

	@Autowired
	private DataSource dataSource;

	@Gegeven("er zit {int} personen in de database.")
	public void generateRandomPeople(int nrOfPeople) {
		runner.plsql(sqlBuilder -> sqlBuilder
			.dataSource(dataSource)
			.statement("DELETE FROM persoon")
		);
		createRandomPersonenAdd(nrOfPeople);
	}

	@Als("de client een nieuwe persoon toevoegt via {string}")
	public void addCorrectPersoon(String url) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.send()
			.post(url)
			.messageType(MessageType.JSON)
			.contentType(ContentType.APPLICATION_JSON.getMimeType())
			.payload("{ \"voornaam\": \"Hans\", \"achternaam\": \"Anders\", \"straat\": \"Kerkstraat\"," +
				" \"huisnummer\": \"88\", \"postcode\": \"2201 EB\", \"woonplaats\": \"Leeuwarden\", \"bsn\": \"777654111\"," +
				" \"geboortedatum\": \"02-12-1958\"}")
		);
	}

	@Als("de client een nieuwe persoon toevoegt via {string} met een bestaand BSN")
	public void addPersoonBsnBestaat(String url) {
		String bsn = "899999999";
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.send()
			.post(url)
			.messageType(MessageType.JSON)
			.contentType(ContentType.APPLICATION_JSON.getMimeType())
			.payload("{ \"voornaam\": \"Hans\", \"achternaam\": \"Anders\", \"straat\": \"Kerkstraat\"," +
				" \"huisnummer\": \"88\", \"postcode\": \"2201 EB\", \"woonplaats\": \"Leeuwarden\", \"bsn\": \"" + bsn + "\"," +
				" \"geboortedatum\": \"02-12-1958\"}")
		);
	}

	@Dan("moet de HTTP status code {int} zijn en huisnummer moet {int} zijn in de response.")
	public void verifyHappyFlow(int httpStatusCode, int numberOfElements) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.receive()
			.response(HttpStatus.valueOf(httpStatusCode))
			.validate("$.huisnummer", String.valueOf(numberOfElements))
		);
	}

	@Dan("moet de HTTP status code {int} zijn en de response is {string}.")
	public void verifyBsnException(int httpStatusCode, String bsnError) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.receive()
			.response(HttpStatus.valueOf(httpStatusCode))
			.validate("$.error", (bsnError))
		);
	}

	private void createRandomPersonenAdd(int nrOfPeople) {
		//Random random = new Random();
		for (int i = 0; i < nrOfPeople; i++) {
			//String bsn = String.valueOf(random.nextInt(899999999) + 100000000);
			String bsn = String.valueOf(899999999 + i);
			int huisnummer = 50 + i;
			runner.plsql(sqlBuilder -> sqlBuilder
				.dataSource(dataSource)
				.statement("INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) " +
					"VALUES ('Karel', 'Derf', 'Fredstraat', " + huisnummer + ", '1234 KL', 'Groningen', " + bsn + ", '01-01-2000')"));
		}
	}
}
