package nl.fuchsia.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class JmsTest {

    @JmsListener(destination = "cjib.mq.example")
    public void handleMessage(TextMessage message) throws JMSException {
        System.out.println("MESSAGE RECEIVED: " + message.getText());
    }

}
