package htcc.employee.service.service.jpa;

import htcc.common.entity.shift.ShiftTime;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.ShiftTimeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ShiftTimeService extends BaseJPAService<ShiftTime, ShiftTime.Key> {

    @Autowired
    private ShiftTimeRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<ShiftTime> findAll() {
        List<ShiftTime> all = new ArrayList<>();

        for (List<ShiftTime> shift : DbStaticConfigMap.SHIFT_TIME_MAP.values()) {
            all.addAll(shift);
        }
        return all;
    }

    @Override
    public ShiftTime findById(ShiftTime.Key key) {
        for (List<ShiftTime> shiftTimes : DbStaticConfigMap.SHIFT_TIME_MAP.values()) {
            for (ShiftTime shift : shiftTimes){
                if (shift.getCompanyId().equals(key.getCompanyId()) &&
                        shift.getOfficeId().equals(key.getOfficeId()) &&
                        shift.getShiftId().equals(key.getShiftId())
                ) {
                    return shift;
                }
            }
        }

        return null;
    }

    public List<ShiftTime> findByCompanyIdAndOfficeId(String companyId, String officeId) {
        return DbStaticConfigMap.SHIFT_TIME_MAP.getOrDefault(companyId + "_" + officeId,
                new ArrayList<>());
    }

    @Override
    public ShiftTime create(ShiftTime entity) {
        ShiftTime newCompany = repo.save(entity);
        hazelcastLoader.loadShiftTimeMap();
        return newCompany;
    }

    @Override
    public ShiftTime update(ShiftTime entity) {
        return create(entity);
    }

    @Override
    public void delete(ShiftTime.Key key) {
        repo.deleteById(key);
        hazelcastLoader.loadShiftTimeMap();
    }

    @Transactional
    public List<ShiftTime> batchInsert(List<ShiftTime> list){
        if (list == null || list.isEmpty()){
            return list;
        }

        List<ShiftTime> result = repo.saveAll(list);
        hazelcastLoader.loadShiftTimeMap();
        return result;
    }

    @Transactional
    public void batchDelete(List<ShiftTime> list){
        if (list == null || list.isEmpty()){
            return;
        }

        repo.deleteAll(list);
        hazelcastLoader.loadShiftTimeMap();
    }
}
