package htcc.log.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.Constant;
import htcc.common.util.LoggingUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Log4j2
@Configuration
@ComponentScan(basePackages = {"htcc.common.component", "htcc.log.service"})
@EntityScan(basePackages = {"htcc.common.entity", "htcc.log.service.entity"})
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Autowired
	private ConfigurableEnvironment configurableEnvironment;

	@Autowired
	private KafkaProducerService kafka;

	@EventListener({ApplicationReadyEvent.class})
	public void readyProcess() throws Exception {
		LoggingUtil.printConfig(configurableEnvironment);
	}

	@Bean
	public void eventRequireIcon() {
		log.info("############## EventRequireIcon ##############");

		kafka.sendMessage(kafka.getBuzConfig().getEventRequireIcon().getTopicName(), Constant.HTCC_LOG_SERVICE);

		log.info("############## End Load EventRequireIcon ##############");
	}
}
