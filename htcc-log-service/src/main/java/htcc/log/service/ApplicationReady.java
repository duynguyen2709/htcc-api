package htcc.log.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;
import util.LoggingUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@Component
public class ApplicationReady {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @EventListener({ApplicationReadyEvent.class})
    public void readyProcess() throws Exception {
        LoggingUtil.printConfig(configurableEnvironment);
    }
}
