package htcc.employee.service.service.jpa;

import htcc.common.entity.role.ManagerRole;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.ManagerRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerRoleService extends BaseJPAService<ManagerRole, ManagerRole.Key> {

    @Autowired
    private ManagerRoleRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<ManagerRole> findAll() {
        return new ArrayList<>(DbStaticConfigMap.MANAGER_ROLE_MAP.values());
    }

    public List<ManagerRole> findByCompanyId(String companyId) {
        return DbStaticConfigMap.MANAGER_ROLE_MAP.values()
                .stream()
                .filter(o -> o.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public ManagerRole findById(ManagerRole.Key key) {
        return DbStaticConfigMap.MANAGER_ROLE_MAP.values().stream()
                .filter(c -> c.getCompanyId().equals(key.getCompanyId()) &&
                        c.getRoleId().equals(key.getRoleId()))
                .findAny().orElse(null);
    }

    @Override
    public ManagerRole create(ManagerRole office) {
        ManagerRole newManagerRole = repo.save(office);
        hazelcastLoader.loadManagerRoleMap();
        return newManagerRole;
    }

    @Override
    public ManagerRole update(ManagerRole office) {
        return create(office);
    }

    @Override
    public void delete(ManagerRole.Key key) {
        repo.deleteById(key);
        hazelcastLoader.loadManagerRoleMap();
    }
}
