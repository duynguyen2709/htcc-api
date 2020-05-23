package htcc.web.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@Log4j2
@Configuration
public class Main {

	@Value("${server.port}")
	private int port;

	@Autowired
	private ConfigurableEnvironment configurableEnvironment;

	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}

		@Bean
		public RestTemplate restTemplate(RestTemplateBuilder builder) {
			return builder.build();
		}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onReady(){
		log.info("************************* APP PROPERTIES ******************************");

		List<MapPropertySource> propertySources = new ArrayList<>();

		configurableEnvironment.getPropertySources().forEach(it -> {
			if (it instanceof MapPropertySource && it.getName().contains("applicationConfig")) {
				propertySources.add((MapPropertySource) it);
			}
		});

		propertySources.stream()
				.map(propertySource -> propertySource.getSource().keySet()).flatMap(Collection::stream).distinct().sorted().forEach(key -> {
			try {
				log.info(key + "=" + configurableEnvironment.getProperty(key));
			} catch (Exception e) {
				log.warn("{} -> {}", key, e.getMessage());
			}
		});
		log.info("************************************************************************************");

		log.info("####### Application Started On Port {} #######", port);
	}
}
