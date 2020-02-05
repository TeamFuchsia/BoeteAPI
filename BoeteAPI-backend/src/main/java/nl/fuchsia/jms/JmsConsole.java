package nl.fuchsia.jms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.annotation.JmsListeners;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class JmsConsole {
	@JmsListener(destination = "cjib.mq.topic")
	public void handleMessageQueue(TextMessage message) throws JMSException {
		System.out.println("MESSAGE RECEIVED: " + message.getText());
	}
}
