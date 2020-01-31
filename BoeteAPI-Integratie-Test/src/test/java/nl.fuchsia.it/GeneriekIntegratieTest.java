package nl.fuchsia.it;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.builder.HttpClientResponseActionBuilder;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import cucumber.api.java.nl.Dan;
import cucumber.api.java.nl.Gegeven;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.sql.DataSource;
import java.util.Map;

public class GeneriekIntegratieTest {

	@CitrusResource
	private TestRunner runner;

	@CitrusEndpoint
	private HttpClient boeteApiClient;

	@Autowired
	private DataSource dataSource;

	@Gegeven("er zitten {int} personen in de database.")
	public void generateRandomPeople(int nrOfPeople) {
		runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement("DELETE FROM persoon"));
		createRandomPersonenAdd(nrOfPeople);
	}

	@Dan("moet de HTTP status code {int} zijn en bevat:")
	public void verifyFlow(int httpStatusCode, DataTable table) {
		runner.http(httpActionBuilder -> {
			HttpClientResponseActionBuilder response = httpActionBuilder.client(boeteApiClient).receive().response(HttpStatus.valueOf(httpStatusCode));

			Map<String, String> keyValuePairs = table.asMap(String.class, String.class);

			keyValuePairs.forEach((key, value) -> response.validate("$." + key, value));
		});
	}

	private void createRandomPersonenAdd(int nrOfPeople) {
		for (int i = 0; i < nrOfPeople; i++) {
			String bsn = String.valueOf(899999999 + i);
			int huisnummer = 50 + i;
			runner.plsql(sqlBuilder -> sqlBuilder.dataSource(dataSource).statement(
				"INSERT INTO persoon (voornaam, achternaam, straat, huisnummer, postcode, woonplaats, bsn, geboortedatum) " + "VALUES ('Karel', 'Derf', 'Fredstraat', " + huisnummer
					+ ", '1234 KL', 'Groningen', " + bsn + ", '01-01-2000')"));
		}
	}
}
