package htcc.employee.service;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.component.redis.RedisClient;
import htcc.common.constant.Constant;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.LoggingUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.GoogleDriveBuzConfig;
import htcc.employee.service.config.ServiceConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

@Log4j2
@Configuration
@ComponentScan(basePackages = {"htcc.common.component",
                               "htcc.employee.service"})
@EntityScan(basePackages = {"htcc.common.entity"})
public class ApplicationReady {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Autowired
    private RedisClient redis;

    @Autowired
    private KafkaProducerService kafka;

    @EventListener({ApplicationReadyEvent.class})
    public void readyProcess() throws Exception {
        LoggingUtil.printConfig(configurableEnvironment);
    }

    @PreDestroy
    public void onDestroy() throws Exception {
        redis.shutdown();
    }

    @Bean
    public void eventRequireIcon() {
        log.info("############## EventRequireIcon ##############");

        kafka.sendMessage(kafka.getBuzConfig().getEventRequireIcon().getTopicName(), Constant.HTCC_EMPLOYEE_SERVICE);

        log.info("############## End Load EventRequireIcon ##############");

    }
}
