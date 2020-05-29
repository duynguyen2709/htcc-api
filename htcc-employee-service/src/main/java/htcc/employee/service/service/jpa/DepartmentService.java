package htcc.employee.service.service.jpa;

import htcc.common.entity.jpa.Department;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService extends BaseJPAService<Department, Department.Key> {

    @Autowired
    private DepartmentRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<Department> findAll() {
        return new ArrayList<>(DbStaticConfigMap.DEPARTMENT_MAP.values());
    }

    public List<Department> findByCompanyId(String companyId) {
        return DbStaticConfigMap.DEPARTMENT_MAP.values()
                .stream()
                .filter(o -> o.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    @Override
    public Department findById(Department.Key key) {
        return DbStaticConfigMap.DEPARTMENT_MAP.values()
                .stream()
                .filter(o -> o.getCompanyId().equals(key.getCompanyId()) && o.getDepartment().equals(key.getDepartment()))
                .findAny().orElse(null);
    }

    @Override
    public Department create(Department office) {
        Department newDepartment = repo.save(office);
        hazelcastLoader.loadDepartmentMap();
        return newDepartment;
    }

    @Override
    public Department update(Department office) {
        return create(office);
    }

    @Override
    public void delete(Department.Key key) {
        repo.deleteById(key);
        hazelcastLoader.loadDepartmentMap();
    }
}
