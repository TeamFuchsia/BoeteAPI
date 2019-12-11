package nl.fuchsia.configuration;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableLoadTimeWeaving
public class DatabaseConfig {

    /**
     * De {@link DataSource} representeert de database connectie.
     * In dit geval maken we gebruik van een lokale postgres installatie.
     *
     * @return De connectie naar de database
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/boeteapi");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        return ds;
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
        adapter.setDatabase(Database.POSTGRESQL);
        return adapter;
    }

    /**
     * Maakt een container met alle relevante informatie van de database en zet het pad waarop de enitites gevonden kunnen worden
     *
     * @param datasource       instellingen van de gebruikte database
     * @param jpaVendorAdapter soort database
     * @return containter om de context naar de database te zetten.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource datasource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(datasource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("nl.fuchsia.model");
        return entityManagerFactoryBean;
    }

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
}