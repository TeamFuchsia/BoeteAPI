package nl.fuchsia.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@Configuration
// To enable scanning for teh @JmsListener annotation
@EnableJms
@Slf4j
public class JMSConfig {

    private boolean isTopic;

    @Bean
    public ActiveMQConnectionFactory connectionFactory(@Value("${jms.url}") String jmsUrl) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(jmsUrl);
        //activeMQConnectionFactory.setTrustAllPackages(true);
        return activeMQConnectionFactory;
    }

    @Bean
    public Destination destination(@Value("broker.destination") String destination) {
        if (destination.endsWith("queue")) {
            this.isTopic = false;
            return new ActiveMQQueue(destination);
        }
        if (destination.endsWith("topic")) {
            this.isTopic = true;
            return new ActiveMQTopic(destination);
        }
        log.error("No topic or queue found");
        throw new IllegalArgumentException("Invalid destination: not a topic or queue");
    }

    // ActiveMQTopic kan ook vervangen worden door een ActiveMQDestination
    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory, Destination destination) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(destination);
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        jmsListenerContainerFactory.setPubSubDomain(this.isTopic);
        return jmsListenerContainerFactory;
    }
}
