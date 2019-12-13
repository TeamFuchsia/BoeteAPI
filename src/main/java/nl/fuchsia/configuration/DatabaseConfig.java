package nl.fuchsia.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig extends AbstractDatabaseConfig {

    /**
     * De {@link DataSource} representeert de database connectie.
     * In dit geval maken we gebruik van een lokale postgres installatie.
     *
     * @return De connectie naar de database
     */
    @Override
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/boeteapi");
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