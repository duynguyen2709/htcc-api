package htcc.gateway.service.config.file;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ZuulRouteConfig {

    @Value("${zuul.routes.htcc-gateway-service.serviceId}")
    public String gatewayService;

    @Value("${zuul.routes.htcc-employee-service.serviceId}")
    public String employeeService;

    @Value("${zuul.routes.htcc-admin-service.serviceId}")
    public String adminService;

}
