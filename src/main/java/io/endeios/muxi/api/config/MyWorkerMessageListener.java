package io.endeios.muxi.api.config;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Random;

@Log
public class MyWorkerMessageListener implements MessageListener {
    private final Random elaborationModule;
    private String id;

    public MyWorkerMessageListener(String id) {
        this.id = id;
        elaborationModule = new Random();
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage acm = (ActiveMQTextMessage) message;

        log.info(id+" - Elaborating message : "+acm.getText());
        int wait = elaborationModule.nextInt(200);
        Thread.sleep(wait);
    }
}
