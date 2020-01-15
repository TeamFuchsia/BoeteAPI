package nl.fuchsia.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;

@Configuration
@Profile(value = "!test")
@PropertySource("classpath:application.properties")
public class DatabaseConfig extends AbstractDatabaseConfig {

    /**
     * De {@link DataSource} representeert de database connectie.
     * In dit geval maken we gebruik van een lokale postgres installatie.
     *
     * @return De connectie naar de database
     */

	@Autowired
	Environment environment;

	@Value("${datasource.url}")
	private String url;

    @Override
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(url);
        //ds.setUrl(environment.getProperty("datasource.url"));
        //ds.setUrl("jdbc:postgresql://localhost:5432/boeteapi");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        return ds;
    }

    /**
     * met welk database type wordt er gewerkt.
     *
     * @return het database type
     */
    @Override
    public Database getDatabaseType() {
        return Database.POSTGRESQL;
    }
}
