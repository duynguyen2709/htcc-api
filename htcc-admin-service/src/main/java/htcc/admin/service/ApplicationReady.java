package htcc.admin.service;

import htcc.common.util.LoggingUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;

@Log4j2
@Configuration
@ComponentScan(basePackages = {"htcc.common.component", "htcc.admin.service"})
@EntityScan(basePackages = {"htcc.common.entity.jpa"})
public class ApplicationReady {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @EventListener({ApplicationReadyEvent.class})
    public void readyProcess() throws Exception {
        LoggingUtil.printConfig(configurableEnvironment);
    }
}
