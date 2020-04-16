package htcc.employee.service.service.jpa;

import htcc.common.entity.jpa.Department;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.repository.jpa.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService extends BaseJPAService<Department, Department.Key> {

    @Autowired
    private DepartmentRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<Department> findAll() {
        return repo.findAll();
    }

    public List<Department> findByCompanyId(String companyId) {
        return repo.findByCompanyId(companyId);
    }

    @Override
    public Department findById(Department.Key key) {
        Optional<Department> office = repo.findById(key);
        return office.orElse(null);
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
