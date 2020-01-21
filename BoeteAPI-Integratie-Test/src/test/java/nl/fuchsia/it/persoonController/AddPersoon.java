package nl.fuchsia.it.persoonController;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import cucumber.api.java.nl.Als;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class AddPersoon {
    /**
     * IT voor het toevoegen van personen
     */
    @CitrusResource
    private TestRunner runner;

    @CitrusEndpoint
    private HttpClient boeteApiClient;

    @Autowired
    private DataSource dataSource;

    @Als("de client een POST maakt naar {string} met")
    public void deClientEenPOSTMaaktNaarMet(String url, String body) {
        runner.http(httpActionBuilder -> httpActionBuilder.client(boeteApiClient).send().post(url).messageType(MessageType.JSON).contentType(ContentType.APPLICATION_JSON.getMimeType()).payload(body));
    }
}