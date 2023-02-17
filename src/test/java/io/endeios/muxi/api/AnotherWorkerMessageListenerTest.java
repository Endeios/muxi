package io.endeios.muxi.api;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.java.Log;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AnotherWorkerMessageListenerTest.TestConfiguration.class)
public class AnotherWorkerMessageListenerTest {


    private static final String LOGGER_NAME = "io.endeios.muxi.api";
    @Autowired
    private BrokerService broker;

    @Autowired
    private JmsTemplate jmsTemplate;

    private TestInMemoryLoggerAppender memoryAppender;

    @Autowired
    private ConnectionFactory connectionFactory;

    @BeforeEach
    void setUp() {
        //Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
        memoryAppender = new TestInMemoryLoggerAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    void exists() {
        var a = new AnotherWorkerMessageListener();
    }

    @Test
    void printsMessageOnConsoleOnMessage() throws JMSException {
        int sizeBefore = memoryAppender.getSize();
        ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("Hello");
        var a = new AnotherWorkerMessageListener();
        a.onMessage(textMessage);
        int sizeAfter = memoryAppender.getSize();
        assertEquals(sizeBefore + 2, sizeAfter);
    }
    @Test
    void activatesOnEvent() throws JMSException {
        memoryAppender.reset();
        int sizeBefore = memoryAppender.getSize();

        jmsTemplate.convertAndSend(TestJmsListenerConfigurer.TEST_TOPIC, "This is a test message");

        int sizeAfter = memoryAppender.getSize();
        assertEquals(sizeBefore + 2, sizeAfter);
    }


    @Configuration
    @EnableJms
    static class TestConfiguration {
        @Bean
        public BrokerService brokerService() throws Exception {
            BrokerService broker = new BrokerService();
            // configure the broker
            broker.setBrokerName("broker");
            broker.addConnector("tcp://localhost:61616");
            broker.start();
            log.info("Starting broker: " + broker);
            return broker;
        }


        @Bean
        @Qualifier("connectionFactory")
        ConnectionFactory connectionFactory() {
            ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
            activeMQConnectionFactory.setUserName("user");
            activeMQConnectionFactory.setPassword("password");
            activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
            log.info("Starting Low-level Connection Factory: " + activeMQConnectionFactory);
            return activeMQConnectionFactory;
        }

        @Bean
        JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
            return new JmsTemplate(connectionFactory);
        }

        @Bean(name = "jmsListenerContainerFactory")
        JmsListenerContainerFactory containerFactory(
                @Qualifier("connectionFactory") ConnectionFactory connectionFactory
        ) {
            DefaultJmsListenerContainerFactory containerFactory = new DefaultJmsListenerContainerFactory();
            containerFactory.setConnectionFactory(connectionFactory);
            return containerFactory;
        }

        @Bean
        JmsListenerConfigurer configurer() {
            return new TestJmsListenerConfigurer();
        }
    }
}
