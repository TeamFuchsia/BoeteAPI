//package nl.fuchsia.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.vendor.Database;
//
//import javax.sql.DataSource;
//
//@Configuration
//@Profile("test")
//public class TestDatabaseConfig extends AbstractDatabaseConfig{
//
//	@Override
//	public DataSource dataSource() throws Exception {
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		ds.setDriverClassName("org.postgresql.Driver");
//		ds.setUrl("jdbc:postgresql://localhost:5432/boeteapi_it");
//		ds.setUsername("postgres");
//		ds.setPassword("postgres");
//		return ds;
//	}
//
//	@Override
//	protected Database getDatabaseType() {
//		return Database.POSTGRESQL;
//	}
//}
