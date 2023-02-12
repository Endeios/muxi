package io.endeios.muxi.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("muxi")
@Data
@Component
public class MuxiConfigProperties {
    String[] workers = new String[]{"default-worker"};

    String brokerName = "embedded";

    Broker broker =new Broker();

    @Data
    public static class Broker {
        String address;
        String username;
        String password;
    }
}
