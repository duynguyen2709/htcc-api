package htcc.gateway.service;

import htcc.gateway.service.component.redis.RedisClient;
import htcc.gateway.service.config.file.ServiceConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;
import util.LoggingUtil;
import util.MemoryUtil;
import util.StringUtil;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class ApplicationReady {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private RedisClient redis;

    @EventListener({ApplicationReadyEvent.class})
    public void readyProcess() throws Exception {
        // print config from file
        LoggingUtil.printConfig(configurableEnvironment);

        // print memory usage for monitoring
        if (serviceConfig.isDebugMode()) {
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

            ses.scheduleAtFixedRate(new Runnable() {
                @Override public void run() {
                    MemoryUtil.printMemory();
                }
            }, 0, 60, TimeUnit.SECONDS);
        }

    }

    @PreDestroy
    public void onDestroy() throws Exception {
        redis.shutdown();
    }
}
