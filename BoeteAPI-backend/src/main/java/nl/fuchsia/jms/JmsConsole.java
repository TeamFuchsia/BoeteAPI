package nl.fuchsia.jms;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsConsole {
    @JmsListener(destination = "cjib.mq.topic")
    public void handleMessageQueue(TextMessage message)
            throws JMSException {
        System.out.println("MESSAGE RECEIVED: " + message.getText());
    }
}
