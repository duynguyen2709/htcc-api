package htcc.employee.service.service.jpa;

import htcc.common.entity.shift.FixedShiftArrangement;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.FixedShiftArrangementRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FixedShiftArrangementService extends BaseJPAService<FixedShiftArrangement, Integer> {

    @Autowired
    private FixedShiftArrangementRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<FixedShiftArrangement> findAll() {
        List<FixedShiftArrangement> all = new ArrayList<>();

        for (List<FixedShiftArrangement> shift : DbStaticConfigMap.FIXED_SHIFT_MAP.values()) {
            all.addAll(shift);
        }
        return all;
    }

    public List<FixedShiftArrangement> findByCompanyId(String companyId) {
        return DbStaticConfigMap.FIXED_SHIFT_MAP.getOrDefault(companyId, new ArrayList<>());
    }

    public List<FixedShiftArrangement> findByCompanyIdAndOfficeIdAndShiftId(String companyId, String officeId, String shiftId) {
        return DbStaticConfigMap.FIXED_SHIFT_MAP.getOrDefault(companyId, new ArrayList<>())
                .stream()
                .filter(c -> c.getOfficeId().equals(officeId) && c.getShiftId().equals(shiftId))
                .collect(Collectors.toList());
    }

    public List<FixedShiftArrangement> findByCompanyIdAndUsername(String companyId, String username) {
        return DbStaticConfigMap.FIXED_SHIFT_MAP.getOrDefault(companyId, new ArrayList<>())
                .stream()
                .filter(c -> c.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public FixedShiftArrangement findById(Integer key) {
        for (List<FixedShiftArrangement> shiftTimes : DbStaticConfigMap.FIXED_SHIFT_MAP.values()) {
            for (FixedShiftArrangement shift : shiftTimes){
                if (shift.getId() == key){
                    return shift;
                }
            }
        }

        return null;
    }

    @Override
    public FixedShiftArrangement create(FixedShiftArrangement entity) {
        FixedShiftArrangement newEnt = repo.save(entity);
        hazelcastLoader.loadFixedShiftArrangementMap();
        return newEnt;
    }

    @Override
    public FixedShiftArrangement update(FixedShiftArrangement entity) {
        return create(entity);
    }

    @Override
    public void delete(Integer key) {
        repo.deleteById(key);
        hazelcastLoader.loadFixedShiftArrangementMap();
    }

    @Transactional
    public void batchDelete(List<FixedShiftArrangement> list){
        if (list == null || list.isEmpty()){
            return;
        }

        repo.deleteAll(list);
        hazelcastLoader.loadFixedShiftArrangementMap();
    }
}
