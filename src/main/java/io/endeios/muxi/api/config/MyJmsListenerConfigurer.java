package io.endeios.muxi.api.config;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

@Log
@Configuration
public class MyJmsListenerConfigurer implements JmsListenerConfigurer {
    @Value("${worker_queues}")
    private String[] workerTopics;

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        for (String topic : workerTopics){
            log.info("Stating up worker for " + topic);
            SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
            endpoint.setId("ID|"+topic);
            endpoint.setDestination(topic);
            endpoint.setMessageListener(new MyWorkerMessageListener());
            registrar.registerEndpoint(endpoint);
        }
    }
}
