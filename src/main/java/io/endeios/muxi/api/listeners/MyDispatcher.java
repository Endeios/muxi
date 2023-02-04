package io.endeios.muxi.api.listeners;

import io.endeios.muxi.utils.SimpleCircularDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyDispatcher {
    private SimpleCircularDispatcher<JmsTemplate> dispatcher;

    @Autowired
    public MyDispatcher(SimpleCircularDispatcher<JmsTemplate> dispatcher) {
        this.dispatcher = dispatcher;
    }

    @JmsListener(destination = "test_dest", containerFactory = "myFactory")
    public void receiveMessage(String a){
        System.out.println("Dispatching "+a);
        dispatcher.getNext().convertAndSend(a);

    }
}
