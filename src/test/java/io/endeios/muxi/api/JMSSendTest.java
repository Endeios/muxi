package io.endeios.muxi.api;

import lombok.extern.java.Log;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
@Log
public class JMSSendTest {

    public static final String DEST = "test_dest";

    @Test
    void send() {
        ActiveMQConnectionFactory cf = getConnectionFactory();
        JmsTemplate jmsTemplate = new JmsTemplate(cf);
        for (int i = 0; i < 1000; i++) {
            jmsTemplate.convertAndSend(DEST,"Hello World "+i);

        }
    }

    @Test
    void receive() {
        JmsTemplate jmsTemplate = new JmsTemplate(getConnectionFactory());
        String data=null;
        do {
            data = (String) jmsTemplate.receiveAndConvert(DEST);
            log.info("data: "+data);
        } while (data!=null);
    }

    private static ActiveMQConnectionFactory getConnectionFactory() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setUserName("user");
        cf.setPassword("password");
        cf.setBrokerURL("tcp://localhost:61616");
        return cf;
    }
}
