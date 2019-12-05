package nl.fuchsia.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    /**
     * De {@link DataSource} representeert de database connectie.
     * In dit geval maken we gebruik van een lokale postgres installatie.
     *
     * @return De connectie naar de database
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/boeteapi");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("postgres");
        return basicDataSource;
    }

    /**
     * JDBC by itself is tough to use, so we wrap it in the {@link JdbcTemplate} from Spring,
     * which handles a lot of the boilerplate for us.
     * Pure JDBC has its uses though. While it gives a lot of boilerplate,
     * it also gives a lot of control, which can be useful for certain applications.
     * Think of having to make very complex queries for very specific situations.
     *
     * @param dataSource
     * @return The Jdbc template from Spring.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
