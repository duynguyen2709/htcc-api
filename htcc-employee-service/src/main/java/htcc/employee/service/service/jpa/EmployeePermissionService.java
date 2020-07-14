package htcc.employee.service.service.jpa;

import htcc.common.entity.role.EmployeePermission;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.repository.jpa.EmployeePermissionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Log4j2
public class EmployeePermissionService extends BaseJPAService<EmployeePermission, EmployeePermission.Key> {

    @Autowired
    private EmployeePermissionRepository repo;

    @Autowired
    private EntityManager em;

    @Override
    public List<EmployeePermission> findAll() {
        return repo.findAll();
    }

    @Override
    public EmployeePermission findById(EmployeePermission.Key key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public EmployeePermission create(EmployeePermission employeeInfo) {
        EmployeePermission newEnt = repo.save(employeeInfo);
        return newEnt;
    }

    @Override
    public EmployeePermission update(EmployeePermission employeeInfo) {
        return create(employeeInfo);
    }

    public List<EmployeePermission> updateAll(List<EmployeePermission> list) {
        return repo.saveAll(list);
    }

    @Override
    public void delete(EmployeePermission.Key key) {
        repo.deleteById(key);
    }
}
