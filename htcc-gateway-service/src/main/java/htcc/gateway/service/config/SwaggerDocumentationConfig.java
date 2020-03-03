package htcc.gateway.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class SwaggerDocumentationConfig implements SwaggerResourcesProvider {

    private static final String VERSION = "2.0";

    @Value("${zuul.routes.htcc-employee-service.service-id}")
    private String employeeService;

    @Value("${spring.application.name}")
    private String gatewayService;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource(gatewayService, "/v2/api-docs"));
        resources.add(swaggerResource(employeeService, "/api/employee/v2/api-docs"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(VERSION);
        return swaggerResource;
    }

}
