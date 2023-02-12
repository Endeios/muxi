package io.endeios.muxi.api;

import io.endeios.muxi.api.config.BrokerConfig;
import io.endeios.muxi.api.config.properties.MuxiConfigProperties;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication()
@EnableJms
@Log
public class Application implements CommandLineRunner {
	@Autowired
	private MuxiConfigProperties muxiConfigProperties;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		log.info("Starting up ");
		log.info("" + muxiConfigProperties.getBrokerName());

	}
}
