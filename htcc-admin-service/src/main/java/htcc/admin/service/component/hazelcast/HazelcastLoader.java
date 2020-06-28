package htcc.admin.service.component.hazelcast;

import htcc.admin.service.jpa.FeaturePriceRepository;
import htcc.admin.service.jpa.NotificationIconConfigRepository;
import htcc.common.component.HazelcastService;
import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.CacheKeyEnum;
import htcc.common.constant.ScreenEnum;
import htcc.common.entity.feature.FeaturePrice;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static htcc.admin.service.config.DbStaticConfigMap.*;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@ComponentScan(basePackages = {"htcc.common.component.kafka"})
@Log4j2
public class HazelcastLoader {

    @Autowired
    private HazelcastService hazelcastService;

    @Autowired
    private NotificationIconConfigRepository notiIconRepo;

    @Autowired
    private KafkaProducerService kafka;

    @Autowired
    private FeaturePriceRepository featurePriceRepository;

    // [important] must init after kafka initialization, otherwise kafka is null
    @Bean
    @DependsOn({"kafkaProducerService"})
    public void loadAllStaticMap() throws Exception {
        log.info("####### Started Loading Static Config Map ########\n");

        loadFeaturePriceMap();

        loadNotiIconConfigMap();

        log.info("####### Loaded All Static Config Map Done ########\n");
    }

    public void loadFeaturePriceMap() {
        Map<String, FeaturePrice> map = new HashMap<>();

        for (FeaturePrice c : featurePriceRepository.findAll()) {
            ScreenEnum screenEnum = ScreenEnum.fromInt(c.getLinkedScreen());
            if (screenEnum == null) {
                log.error("Screen " + c.getLinkedScreen() + " is not supported");
                System.exit(3);
            }
            c.setDisplayScreen(screenEnum.getScreenDescription());
            map.put(c.getFeatureId(), c);
        }

        SUPPORTED_FEATURE_MAP = hazelcastService.reload(map, CacheKeyEnum.SUPPORTED_FEATURE);
        log.info("[loadFeaturePriceMap] SUPPORTED_FEATURE_MAP loaded succeed \n{}",
                StringUtil.toJsonString(SUPPORTED_FEATURE_MAP));

    }

    public void loadNotiIconConfigMap() {
        Map<String, NotificationIconConfig> map = new HashMap<>();

        notiIconRepo.findAll().forEach(c -> map.put(c.getIconId(), c));

        NOTI_ICON_MAP = hazelcastService.reload(map, CacheKeyEnum.NOTI_ICON);
        log.info("[loadNotiIconConfigMap] NOTI_ICON_MAP loaded succeed \n{}", StringUtil.toJsonString(NOTI_ICON_MAP));

        // send kafka message here for employee & log service to get list icon
        kafka.sendMessage(kafka.getBuzConfig().getEventLoadIcon().getTopicName(), StringUtil.toJsonString(NOTI_ICON_MAP));
    }
}
