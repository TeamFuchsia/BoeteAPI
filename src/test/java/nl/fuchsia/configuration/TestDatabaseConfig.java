package nl.fuchsia.configuration;

import java.util.Properties;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.Database;

@Configuration
@ComponentScan("nl.fuchsia.repository")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestDatabaseConfig extends AbstractDatabaseConfig {

    /**
     * De {@link DataSource} representeert de database connectie.
     * In dit geval maken we gebruik van een interne H2 database installatie.
     *
     * @return De connectie naar de database
     */
    @Override
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    /**
     * met welk database type wordt er gewerkt.
     *
     * @return het database type
     */
    @Override
    public Database getDatabaseType() {
        return Database.H2;
    }

    /**
     * welke JpaProperties krijgt de entityManagerFactoryBean mee.
     *
     * @return de JpaProperties
     */
    @Override
    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_ONLY);
        return properties;
    }
}