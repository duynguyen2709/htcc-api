package htcc.admin.service;

import htcc.common.component.annotation.EnableSwaggerDocs;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableSwaggerDocs(basePackage = "htcc.admin.service.controller",
                   baseUrl = "https://1612145.online/api/admin/")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
