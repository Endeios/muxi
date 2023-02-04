package io.endeios.muxi.api.config;

import io.endeios.muxi.utils.SimpleCircularDispatcher;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;

@Log
@Component
public class DispatcherConfig {
    @Value("${worker_queues}")
    private String[] workerTopics;


    @Bean
    public SimpleCircularDispatcher<JmsTemplate> circularDispatcher(ConnectionFactory connectionFactory){
        List<JmsTemplate> templateList = new ArrayList<>(workerTopics.length);
        for (int i = 0; i < workerTopics.length; i++) {
            String topic = workerTopics[i];
            JmsTemplate template = new JmsTemplate(connectionFactory);
            template.setDefaultDestinationName(topic);
            templateList.add(template);
            log.info("Dispatching template for "+topic);
        }

        return new SimpleCircularDispatcher<>(templateList);

    }

}
