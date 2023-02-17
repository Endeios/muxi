package io.endeios.muxi.api;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class AnotherWorkerMessageListener implements MessageListener {

    Logger logger = LoggerFactory.getLogger(AnotherWorkerMessageListener.class);
    @SneakyThrows
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        logger.debug("Received "+textMessage);
        logger.info("Message: "+ textMessage.getText());

    }
}
