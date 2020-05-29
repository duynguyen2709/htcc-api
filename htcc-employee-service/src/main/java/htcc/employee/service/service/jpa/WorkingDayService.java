package htcc.employee.service.service.jpa;

import htcc.common.entity.workingday.WorkingDay;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.WorkingDayRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class WorkingDayService extends BaseJPAService<WorkingDay, Integer> {

    @Autowired
    private WorkingDayRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<WorkingDay> findAll() {
        List<WorkingDay> all = new ArrayList<>();

        for (List<WorkingDay> workingDays : DbStaticConfigMap.WORKING_DAY_MAP.values()) {
            all.addAll(workingDays);
        }
        return all;
    }

    @Override
    public WorkingDay findById(Integer key) {
        for (List<WorkingDay> workingDays : DbStaticConfigMap.WORKING_DAY_MAP.values()) {
            for (WorkingDay d : workingDays){
                if (d.getId() == key){
                    return d;
                }
            }
        }

        return null;
    }

    public List<WorkingDay> findByCompanyIdAndOfficeId(String companyId, String officeId) {
        return DbStaticConfigMap.WORKING_DAY_MAP.getOrDefault(companyId + "_" + officeId,
                new ArrayList<>());
    }

    @Override
    public WorkingDay create(WorkingDay entity) {
        WorkingDay newCompany = repo.save(entity);
        try {
            hazelcastLoader.loadWorkingDayMap();
        } catch (Exception e) {
            log.error("[hazelcastLoader.loadWorkingDayMap] ex", e);
        }
        return entity;
    }

    @Override
    public WorkingDay update(WorkingDay entity) {
        return create(entity);
    }

    @Override
    public void delete(Integer key) {
        repo.deleteById(key);
        try {
            hazelcastLoader.loadWorkingDayMap();
        } catch (Exception e) {
            log.error("[hazelcastLoader.loadWorkingDayMap] ex", e);
        }
    }

    @Transactional
    public List<WorkingDay> batchInsert(List<WorkingDay> list) throws Exception {
        if (list == null || list.isEmpty()){
            return list;
        }

        List<WorkingDay> result = repo.saveAll(list);
        hazelcastLoader.loadWorkingDayMap();
        return result;
    }

    @Transactional
    public void batchDelete(List<WorkingDay> list) throws Exception {
        if (list == null || list.isEmpty()){
            return;
        }

        repo.deleteAll(list);
        hazelcastLoader.loadWorkingDayMap();
    }
}
