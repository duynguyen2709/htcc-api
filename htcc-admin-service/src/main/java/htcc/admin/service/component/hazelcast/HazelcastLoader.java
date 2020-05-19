package htcc.admin.service.component.hazelcast;

import htcc.admin.service.jpa.NotificationIconConfigRepository;
import htcc.common.component.HazelcastService;
import htcc.common.constant.CacheKeyEnum;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static htcc.admin.service.config.DbStaticConfigMap.*;

@Component
@Log4j2
public class HazelcastLoader {

    @Autowired
    private HazelcastService hazelcastService;

    @Autowired
    private NotificationIconConfigRepository notiIconRepo;

    @PostConstruct
    public void loadAllStaticMap() throws Exception {
        log.info("####### Started Loading Static Config Map ########\n");

        loadNotiIconConfigMap();

        log.info("####### Loaded All Static Config Map Done ########\n");
    }

    public void loadNotiIconConfigMap() {
        Map<String, NotificationIconConfig> map = new HashMap<>();

        notiIconRepo.findAll().forEach(c -> map.put(c.getIconId(), c));

        NOTI_ICON_MAP = hazelcastService.reload(map, CacheKeyEnum.NOTI_ICON);
        log.info("[loadNotiIconConfigMap] NOTI_ICON_MAP loaded succeed [{}]", StringUtil.toJsonString(NOTI_ICON_MAP));
    }
}
