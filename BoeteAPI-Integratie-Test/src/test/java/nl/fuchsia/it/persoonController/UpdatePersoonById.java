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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdatePersoonById {
	/**
	 * IT voor het wijzigen van een persoon.
	 */
	@CitrusResource
	private TestRunner runner;

	@CitrusEndpoint
	private HttpClient boeteApiClient;

	@Autowired
	private DataSource dataSource;

	private String persoonnr;

	@Gegeven("er zit {int} persoon in de database.")
	public void generateRandomPeople(int nrOfPeople) {
		runner.plsql(sqlBuilder -> sqlBuilder
			.dataSource(dataSource)
			.statement("DELETE FROM persoon")
		);
		createRandomPersonenAdd(nrOfPeople);
		ExecuteSQLQueryAction query = runner.query(action -> action
			.dataSource(dataSource)
			.statement("SELECT PERSOONNR FROM public.persoon LIMIT 1")
			.extract("PERSOONNR", "selectPersoonnr")
		);
		persoonnr = runner.variable("selectPersoonnr", "${selectPersoonnr}");
	}

	@Als("de client een PUT updatePersoonById met een {string} invoer maakt naar {string}")
	public void updCorrectPersoon(String invoer, String url) {
		runner.query(action -> action
			.dataSource(dataSource)
			.statement("SELECT * FROM public.persoon LIMIT 1")
		);
		String newBsn = "112233445";
		String existBsn = "900000000";
		String geboortedatumDb = runner.variable("GEBOORTEDATUM", "${GEBOORTEDATUM}");
		DateTimeFormatter dateFromDb = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(geboortedatumDb, dateFromDb);
		DateTimeFormatter dateToJson = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		String geboortedatum = dateToJson.format(date);
		String payload = "";
		switch(invoer){
			case "goede":
				payload = ("{ \"persoonnr\": ${selectPersoonnr}, \"voornaam\": \"${VOORNAAM}\", \"achternaam\": \"${ACHTERNAAM}\", \"straat\": \"${STRAAT}\"," +
					" \"huisnummer\": \"${HUISNUMMER}\", \"postcode\": \"${POSTCODE}\", \"woonplaats\": \"${WOONPLAATS}\", \"bsn\": \"" + newBsn + "\"," +
					" \"geboortedatum\": \"" + geboortedatum + "\"}");
				break;
			case "geen persoonnr":
				payload = ("{ \"voornaam\": \"${VOORNAAM}\", \"achternaam\": \"${ACHTERNAAM}\", \"straat\": \"${STRAAT}\"," +
					" \"huisnummer\": \"${HUISNUMMER}\", \"postcode\": \"${POSTCODE}\", \"woonplaats\": \"${WOONPLAATS}\", \"bsn\": \"" + newBsn + "\"," +
					" \"geboortedatum\": \"" + geboortedatum + "\"}");
				break;
			case "fout persoonnr":
					payload = ("{ \"persoonnr\": 0, \"voornaam\": \"${VOORNAAM}\", \"achternaam\": \"${ACHTERNAAM}\", \"straat\": \"${STRAAT}\"," +
						" \"huisnummer\": \"${HUISNUMMER}\", \"postcode\": \"${POSTCODE}\", \"woonplaats\": \"${WOONPLAATS}\", \"bsn\": \"" + newBsn + "\"," +
						" \"geboortedatum\": \"" + geboortedatum + "\"}");
				break;
			case "bestaand BSN":
				payload = ("{ \"persoonnr\": ${selectPersoonnr}, \"voornaam\": \"${VOORNAAM}\", \"achternaam\": \"${ACHTERNAAM}\", \"straat\": \"${STRAAT}\"," +
					" \"huisnummer\": \"${HUISNUMMER}\", \"postcode\": \"${POSTCODE}\", \"woonplaats\": \"${WOONPLAATS}\", \"bsn\": \"" + existBsn + "\"," +
					" \"geboortedatum\": \"" + geboortedatum + "\"}");
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

	@Dan("moet de HTTP status code {int} zijn en moet in de response o.a het gewijzigde BSN nummer {int} zitten")
	public void verifyResponse(int httpStatusCode, int bsn) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.receive()
			.response(HttpStatus.valueOf(httpStatusCode))
			.validate("$.bsn", String.valueOf(bsn))
		);

	}

	@Dan("moet de HTTP status code {int} zijn en de response {string} bevat.")
	public void NoPersoonrException(int httpStatusCode, String noPersoonnr) {
		runner.http(httpActionBuilder -> httpActionBuilder
			.client(boeteApiClient)
			.receive()
			.response(HttpStatus.valueOf(httpStatusCode))
			.validate("$.error", (noPersoonnr))
		);
	}

	private void createRandomPersonenAdd(int nrOfPeople) {
		for (int i = 0; i < nrOfPeople; i++) {
			String bsn = String.valueOf(899999999 + i);
			int huisnummer = 50 + i;
			runner.plsql(sqlBuilder -> sqlBuilder
				.dataSource(dataSource)
				.statement("INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) " +
					"VALUES ('Karel', 'Derf', 'Fredstraat', " + huisnummer + ", '1234 KL', 'Groningen', " + bsn + ", '15-01-2000')"));
		}
	}
}
