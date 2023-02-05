package io.endeios.muxi.api.config;

import lombok.extern.java.Log;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;

@Log
@Configuration
public class BrokerConfig {

    @Bean
    public BrokerService brokerService() throws Exception {
        BrokerService broker = new BrokerService();
        // configure the broker
        broker.setBrokerName("broker");
        broker.addConnector("tcp://localhost:61616");
        broker.start();
        log.info("Starting broker: "+broker);
        return broker;
    }
    @Bean
    public JmsListenerContainerFactory<?> myFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        log.info("Starting Listener Connection Factory: "+factory);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
                                                                      ConnectionFactory connectionFactory,
                                                                      DefaultJmsListenerContainerFactoryConfigurer configurer){
        return myFactory(connectionFactory, configurer);
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(){
        return new CachingConnectionFactory(connectionFactory());
    }


    @Bean
    @Qualifier("connectionFactory")
    ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setUserName("user");
        activeMQConnectionFactory.setPassword("password");
        activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
        log.info("Starting Low-level Connection Factory: "+activeMQConnectionFactory);
        return activeMQConnectionFactory;
    }


}

