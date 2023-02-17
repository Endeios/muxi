package io.endeios.muxi.api;

import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;

public class TestJmsListenerConfigurer implements JmsListenerConfigurer {
    public static final String TEST_TOPIC = "TEST_TOPIC";

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {

        SimpleJmsListenerEndpoint simpleJmsListenerEndpoint = new SimpleJmsListenerEndpoint();
        simpleJmsListenerEndpoint.setMessageListener(new AnotherWorkerMessageListener());
        simpleJmsListenerEndpoint.setId("TEST");
        simpleJmsListenerEndpoint.setDestination(TEST_TOPIC);
        registrar.registerEndpoint(simpleJmsListenerEndpoint);
    }
}
