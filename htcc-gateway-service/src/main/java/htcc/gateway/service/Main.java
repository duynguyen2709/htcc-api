package htcc.gateway.service;

import htcc.common.component.annotation.EnableSwaggerDocs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaServer
@EnableZuulProxy
@EnableSwagger2
@EnableSwaggerDocs(basePackage = "htcc.gateway.service.controller",
                   baseUrl = "https://1612145.online/api/gateway/")
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
