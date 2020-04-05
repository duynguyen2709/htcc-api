package htcc.employee.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "service")
public class ServiceConfig {
    public boolean      debugMode                  = false;
    public boolean      allowDeleteCheckin         = false;
    public long         maxFileSize                = 0L;
    public List<String> leavingRequestCategoryList = new ArrayList<>();

}
