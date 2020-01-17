package nl.fuchsia.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configureert de WebApplicationInitializer
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "nl.fuchsia")
@PropertySource(value = {"classpath:application-local.properties","classpath:application-${spring.profiles.active}.properties"}, ignoreResourceNotFound = true)
public class WebConfig implements WebMvcConfigurer {

    /**
     * Om één of andere reden is deze bean statisch nodig. Het is nog niet duidelijk waarom,
     * maar zonder deze bean wordt @Value niet geëvalueerd.
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
