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
@EnableLoadTimeWeaving
@EnableTransactionManagement
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
        // TODO  niet hardcoded maar via properties
        ds.setUrl("jdbc:postgresql://localhost:5432/boeteapi");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        return ds;
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

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(emf);
        return jpaTransactionManager;
    }

    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("nl.fuchsia.model");
        return entityManagerFactoryBean;
    }
}