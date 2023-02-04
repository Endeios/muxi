package io.endeios.muxi.api.config;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.jms.Message;
import javax.jms.MessageListener;
@Log
public class MyWorkerMessageListener implements MessageListener {
    @SneakyThrows
    @Override
    public void onMessage(Message message) {

        log.info("Elaborating message (Hello World!): "+message.getBody(String.class));
    }
}
