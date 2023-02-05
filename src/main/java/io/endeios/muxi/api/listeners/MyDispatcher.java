package io.endeios.muxi.api.listeners;

import io.endeios.muxi.utils.SimpleCircularDispatcher;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyDispatcher {
    private SimpleCircularDispatcher<JmsTemplate> dispatcher;
    private static final ActiveMQDestination replyDestination = new ActiveMQQueue("reply_destination");

    @Autowired
    public MyDispatcher(SimpleCircularDispatcher<JmsTemplate> dispatcher) {
        this.dispatcher = dispatcher;
    }

    @JmsListener(destination = "test_dest", containerFactory = "myFactory")
    public void receiveMessage(String a){
        System.out.println("Dispatching "+a);
        JmsTemplate jmsTemplate = dispatcher.getNext();
        jmsTemplate.convertAndSend(a,message -> {
            message.setJMSReplyTo(replyDestination);
            return message;
        });

    }

    @JmsListener(destination = "reply_destination" )
    public void getReply(String reply){
        System.out.println("Got reply: "+reply);
    }
}
