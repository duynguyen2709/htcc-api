package htcc.gateway.service;

import htcc.common.component.redis.RedisClient;
import htcc.common.util.LoggingUtil;
import htcc.common.util.MemoryUtil;
import htcc.gateway.service.config.file.ServiceConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
@Configuration
@ComponentScan(basePackages = {"htcc.common.component", "htcc.gateway.service"})
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

//        // print memory usage for monitoring
//        if (serviceConfig.isDebugMode()) {
//            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
//
//            ses.scheduleAtFixedRate(new Runnable() {
//                @Override public void run() {
//                    MemoryUtil.printMemory();
//                }
//            }, 0, 30, TimeUnit.SECONDS);
//        }

    }

    @PreDestroy
    public void onDestroy() throws Exception {
        redis.shutdown();
    }
}
