package nl.fuchsia.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig extends AbstractDatabaseConfig {

    @Value("${datasource.url}")
    public String url;
    @Value("${datasource.username}")
    public String username;
    @Value("${datasource.password}")
    public String password;
    /**
     * De {@link DataSource} representeert de database connectie.
     * In dit geval maken we gebruik van een lokale postgres installatie.
     *
     * @return De connectie naar de database
     */
    @Value("${datasource.driver}")
    private String driver;

    @Override
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
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
