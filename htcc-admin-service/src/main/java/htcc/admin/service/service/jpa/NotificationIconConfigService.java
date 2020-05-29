package htcc.admin.service.service.jpa;

import htcc.admin.service.component.hazelcast.HazelcastLoader;
import htcc.admin.service.config.DbStaticConfigMap;
import htcc.admin.service.jpa.NotificationIconConfigRepository;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.service.BaseJPAService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class NotificationIconConfigService extends BaseJPAService<NotificationIconConfig, String> {

    @Autowired
    private NotificationIconConfigRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<NotificationIconConfig> findAll() {
        return new ArrayList<>(DbStaticConfigMap.NOTI_ICON_MAP.values());
    }

    @Override
    public NotificationIconConfig findById(String key) {
        return DbStaticConfigMap.NOTI_ICON_MAP.getOrDefault(key, null);
    }

    @Override
    public NotificationIconConfig create(NotificationIconConfig icon) {
        NotificationIconConfig newCompany = repo.save(icon);
        hazelcastLoader.loadNotiIconConfigMap();
        return icon;
    }

    @Override
    public NotificationIconConfig update(NotificationIconConfig icon) {
        return create(icon);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
        hazelcastLoader.loadNotiIconConfigMap();
    }
}
