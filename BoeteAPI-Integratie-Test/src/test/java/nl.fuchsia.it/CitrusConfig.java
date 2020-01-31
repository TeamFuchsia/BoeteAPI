package nl.fuchsia.it;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@Configuration
public class CitrusConfig {
	@Bean
	public HttpClient boeteApiClient() {
		return CitrusEndpoints
			.http()
			.client()
			.requestUrl("http://localhost:8080")
			.build();
	}

	@Bean
	public SingleConnectionDataSource dataSource() {
		SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUrl("jdbc:postgresql://localhost:5432/boeteapi_it");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		return dataSource;
	}
}
