package htcc.log.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import htcc.common.util.LoggingUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
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
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

//	private static final String tokenPush = "dvFIaV7f7kc:APA91bG98IfyBTPKayZweB4zS7vbPeG6vmtBfMk2gDCSOWrJrJnOJ1fKYuGHzTHitRE4UmwvymZ6avSaQ4mPru9FRgRGwT7YupMRtEVpIXX28A1HBKvXq7T3KJw1jQqrNMgWDTynVEHL";

	@Autowired
	private ConfigurableEnvironment configurableEnvironment;

	@EventListener({ApplicationReadyEvent.class})
	public void readyProcess() throws Exception {
		LoggingUtil.printConfig(configurableEnvironment);
	}

//	public String sendPnsToDevice() {
//		log.info("\n\n");
//
//		Notification noti = Notification.builder().setTitle("TEST FROM SERVER")
//				.setBody("this is a message from server").build();
//
//		Message message = Message.builder()
//				.setToken(tokenPush)
//				.setNotification(noti)
//				.putData("title", "TEST FROM SERVER")
//				.putData("body", "this is a message from server")
//				.build();
//
//		String response = "";
//		try {
//			response = FirebaseMessaging.getInstance().send(message);
//		} catch (Exception e) {
//			log.error("Fail to send firebase notification", e);
//		}
//		log.info("\n\n\n\n");
//
//		return response;
//	}
}
