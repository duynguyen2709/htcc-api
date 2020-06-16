package htcc.employee.service.service.jpa;

import htcc.common.entity.shift.ShiftArrangementTemplate;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.ShiftArrangementTemplateRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ShiftArrangementTemplateService extends BaseJPAService<ShiftArrangementTemplate, Integer> {

    @Autowired
    private ShiftArrangementTemplateRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<ShiftArrangementTemplate> findAll() {
        List<ShiftArrangementTemplate> all = new ArrayList<>();

        for (List<ShiftArrangementTemplate> shift : DbStaticConfigMap.SHIFT_TEMPLATE_MAP.values()) {
            all.addAll(shift);
        }
        return all;
    }

    public List<ShiftArrangementTemplate> findByCompanyId(String companyId) {
        return DbStaticConfigMap.SHIFT_TEMPLATE_MAP.getOrDefault(companyId, new ArrayList<>());
    }

    @Override
    public ShiftArrangementTemplate findById(Integer key) {
        for (List<ShiftArrangementTemplate> shiftTimes : DbStaticConfigMap.SHIFT_TEMPLATE_MAP.values()) {
            for (ShiftArrangementTemplate shift : shiftTimes){
                if (shift.getTemplateId() == key){
                    return shift;
                }
            }
        }

        return null;
    }

    @Override
    public ShiftArrangementTemplate create(ShiftArrangementTemplate entity) {
        ShiftArrangementTemplate newEnt = repo.save(entity);
        hazelcastLoader.loadShiftArrangementTemplateMap();
        return newEnt;
    }

    @Override
    public ShiftArrangementTemplate update(ShiftArrangementTemplate entity) {
        return create(entity);
    }

    @Override
    public void delete(Integer key) {
        repo.deleteById(key);
        hazelcastLoader.loadShiftArrangementTemplateMap();
    }
}
