//package nl.fuchsia.configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.eclipse.persistence.config.PersistenceUnitProperties;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
//import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
//import org.springframework.transaction.jta.JtaTransactionManager;
//
//@Configuration
//public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {
//
//    @Override
//    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
//        return new EclipseLinkJpaVendorAdapter();
//    }
//
//    @Override
//    protected Map<String, Object> getVendorProperties() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(PersistenceUnitProperties.WEAVING, true);
//        return map;
//    }
//}
