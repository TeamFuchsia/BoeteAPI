/*
 * Copyright 2006-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.fuchsia.it;

import com.consol.citrus.db.driver.JdbcDriver;
import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.jdbc.server.JdbcServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * @author Christoph Deppisch
 */
@Configuration
public class CitrusConfig {

    @Bean
    public HttpClient boeteApiClient() {
        return CitrusEndpoints
                .http()
                .client()
                .requestUrl("http://localhost:8080")
                .build();
    }

    //
//    @Bean
//    public JdbcServer jdbcServer() {
//        return CitrusEndpoints.jdbc()
//                .server()
//                .host("localhost")
//                .databaseName("boeteapi")
//                .port(5432)
//                .autoStart(true) // TODO is dit nodig?
//                .build();
//    }
//
    @Bean
    public SingleConnectionDataSource dataSource() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName(JdbcDriver.class.getName());
        dataSource.setUrl("jdbc:citrus:http://localhost:5432/boeteapi");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }
}
