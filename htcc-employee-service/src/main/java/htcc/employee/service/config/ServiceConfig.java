package htcc.employee.service.config;

import htcc.employee.service.entity.config.LeavingRequestCategory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "service")
public class ServiceConfig {
    public boolean              debugMode                  = false;
    public boolean              allowDeleteCheckin         = false;
    public long                 maxFileSize                = 0L;
    public Map<String, Boolean> leavingRequestCategoryList = new HashMap<>();

    public void setLeavingRequestCategoryList(List<LeavingRequestCategory> list){
        for (LeavingRequestCategory c : list){
            this.leavingRequestCategoryList.put(c.reason, (c.useDayOff == 1));
        }
    }
}
