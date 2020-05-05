package htcc.employee.service;

import htcc.common.component.annotation.EnableSwaggerDocs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableSwaggerDocs(basePackage = "htcc.employee.service.controller",
                   baseUrl = "https://1612145.online/api/employee/")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
