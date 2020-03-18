package htcc.gateway.service.config;

import htcc.gateway.service.config.file.ZuulRouteConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("Gateway", "/v2/api-docs"));
        resources.add(swaggerResource("Admin", "/api/admin/v2/api-docs"));
        resources.add(swaggerResource("Nhân Viên", "/api/employee/v2/api-docs"));
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
