package nl.fuchsia.config;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
// To enable scanning for teh @JmsListener annotation
@EnableJms
public class JMSConfig {

    private boolean isTopic;

    @Bean
    public ActiveMQConnectionFactory connectionFactory(@Value("${jms.url}") String jmsUrl) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(jmsUrl);
        return activeMQConnectionFactory;
    }

    @Bean
    public Destination destination(@Value("${jms.destination}") String destination) {
        if (destination.endsWith("queue")) {
            this.isTopic = false;
            return new ActiveMQQueue(destination);
        } else if (destination.endsWith("topic")) {
            this.isTopic = true;
            return new ActiveMQQueue(destination);
        }
        throw new IllegalArgumentException("invaliade destination gevonden:destination is geen topic of queue");
    }


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
