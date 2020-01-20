package nl.fuchsia.configuration;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@EnableLoadTimeWeaving
public abstract class AbstractDatabaseConfig {

    /**
     * The {@link DataSource} representing the database connection.
     *
     * @return The connection to the database
     */
    @Bean
    public abstract DataSource dataSource()
            throws Exception;

    /**
     * Deze zorgt er voor dat de data door middel van een transactie in de database wordt geplaatst.
     *
     * @param emf heeft hij nodig om de koppeling tussen de datasource en de entities te maken.
     * @return is de transactionmanager die de commits richting de database maakt.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(emf);
        return jpaTransactionManager;
    }

    /**
     * Geeft begrijpelijkere errors, indien nodig.
     *
     * @return een vertaling van een exception.
     */
    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * Implementatie van het soort database in het project.
     *
     * @return de adapter specifiek bij deze soort database.
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        adapter.setDatabase(getDatabaseType());
        return adapter;
    }

    /**
     * Maakt een container met alle relevante informatie van de database en zet het pad waarop de enitites gevonden kunnen worden
     *
     * @param dataSource       instellingen van de gebruikte database
     * @param jpaVendorAdapter soort database
     * @return containter om de context naar de database te zetten.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setJpaProperties(getProperties());
        entityManagerFactoryBean.setPackagesToScan("nl.fuchsia.model");
        return entityManagerFactoryBean;
    }

    /**
     * welke JpaProperties krijgt de entityManagerFactoryBean mee.
     *
     * @return de JpaProperties
     */
    protected Properties getProperties() {
        return new Properties();
    }

    /**
     * met welk database type wordt er gewerkt.
     *
     * @return het database type
     */
    protected abstract Database getDatabaseType();

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
