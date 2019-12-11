package nl.fuchsia.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Override
    public Database getDatabaseType() {
        return Database.POSTGRESQL;
    }

    /**
     * JDBC op zichzelf is moeilijk te gebruiken, daarom stoppen we dat in {@link JdbcTemplate} van Spring,
     * deze handelt de boilerplate voor ons af.
     *
     * @param dataSource database instellingen
     * @return De Jdbc template van Spring.
     */
    /*
    Pure JDBC kan in sommige gevallen ook nuttig zijn, ondanks de grote hoeveelheid boilerplate.
    Het geeft je veel meer controle, dit kan nuttig zijn voor bepaalde applicaties die hele complexe queries nodig heeft.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}