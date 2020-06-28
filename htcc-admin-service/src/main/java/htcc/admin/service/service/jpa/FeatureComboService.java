package htcc.admin.service.service.jpa;

import htcc.admin.service.component.hazelcast.HazelcastLoader;
import htcc.admin.service.config.DbStaticConfigMap;
import htcc.admin.service.jpa.FeatureComboRepository;
import htcc.common.entity.feature.FeatureCombo;
import htcc.common.service.BaseJPAService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class FeatureComboService extends BaseJPAService<FeatureCombo, String> {

    @Autowired
    private FeatureComboRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<FeatureCombo> findAll() {
        return new ArrayList<>(DbStaticConfigMap.FEATURE_COMBO_MAP.values());
    }

    @Override
    public FeatureCombo findById(String key) {
        return DbStaticConfigMap.FEATURE_COMBO_MAP.getOrDefault(key, null);
    }

    @Override
    public FeatureCombo create(FeatureCombo entity) {
        FeatureCombo newEnt = repo.save(entity);
        hazelcastLoader.loadFeatureComboMap();
        return newEnt;
    }

    @Override
    public FeatureCombo update(FeatureCombo entity) {
        return create(entity);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
        hazelcastLoader.loadFeatureComboMap();
    }
}
