import javax.sql.DataSource;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.config.CitrusSpringConfig;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import cucumber.api.java.nl.Gegeven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CitrusSpringConfig.class)
public class GetFeiten {

    @CitrusResource
            private TestRunner runner;

    @CitrusEndpoint
            private HttpClient boeteApiClient;

    @Autowired
            private DataSource dataSource;

      @Gegeven("er zitten {int} personen in de database")
    public

}
