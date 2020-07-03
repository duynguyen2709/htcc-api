package htcc.admin.service.service.jpa;

import htcc.admin.service.component.hazelcast.HazelcastLoader;
import htcc.admin.service.config.DbStaticConfigMap;
import htcc.admin.service.jpa.FeaturePriceRepository;
import htcc.common.entity.feature.FeaturePrice;
import htcc.common.service.BaseJPAService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class FeaturePriceService extends BaseJPAService<FeaturePrice, String> {

    @Autowired
    private FeaturePriceRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<FeaturePrice> findAll() {
        return new ArrayList<>(DbStaticConfigMap.SUPPORTED_FEATURE_MAP.values());
    }

    @Override
    public FeaturePrice findById(String key) {
        return DbStaticConfigMap.SUPPORTED_FEATURE_MAP.getOrDefault(key, null);
    }

    @Override
    public FeaturePrice create(FeaturePrice entity) {
        FeaturePrice newEnt = repo.save(entity);
        hazelcastLoader.loadFeaturePriceMap();
        return newEnt;
    }

    @Override
    public FeaturePrice update(FeaturePrice entity) {
        return create(entity);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
        hazelcastLoader.loadFeaturePriceMap();
    }
}
