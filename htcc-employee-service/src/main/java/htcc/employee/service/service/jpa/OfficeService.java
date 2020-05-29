package htcc.employee.service.service.jpa;

import htcc.common.entity.jpa.Office;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeService extends BaseJPAService<Office, Office.Key> {

    @Autowired
    private OfficeRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<Office> findAll() {
        return new ArrayList<>(DbStaticConfigMap.OFFICE_MAP.values());
    }

    public List<Office> findByCompanyId(String companyId) {
        return DbStaticConfigMap.OFFICE_MAP.values()
                .stream()
                .filter(o -> o.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public Office findById(Office.Key key) {
        return DbStaticConfigMap.OFFICE_MAP.values().stream()
                .filter(c -> c.getCompanyId().equals(key.getCompanyId()) && c.getOfficeId().equals(key.getOfficeId()))
                .findAny().orElse(null);
    }

    @Override
    public Office create(Office office) {
        Office newOffice = repo.save(office);
        hazelcastLoader.loadOfficeMap();
        return newOffice;
    }

    @Override
    public Office update(Office office) {
        return create(office);
    }

    @Override
    public void delete(Office.Key key) {
        repo.deleteById(key);
        hazelcastLoader.loadOfficeMap();
    }

    public Office findHeadquarter(String companyId) {
        return DbStaticConfigMap.OFFICE_MAP.values()
                .stream()
                .filter(o -> o.getCompanyId().equals(companyId) && o.isHeadquarter)
                .findFirst().orElse(null);
    }
}
