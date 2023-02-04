package io.endeios.muxi.api.listeners;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MyDispatcher {
    @JmsListener(destination = "test_dest", containerFactory = "myFactory")
    public void receiveMessage(String a){
        System.out.println("Dispatching "+a);

    }
}
