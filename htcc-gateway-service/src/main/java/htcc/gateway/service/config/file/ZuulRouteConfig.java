package htcc.gateway.service.config.file;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ZuulRouteConfig {

    @Value("${spring.application.name}")
    public String gatewayService;

    @Value("${zuul.routes.htcc-employee-service.service-id}")
    public String employeeService;

    @Value("${zuul.routes.htcc-admin-service.service-id}")
    public String adminService;

}
