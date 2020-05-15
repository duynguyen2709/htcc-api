package htcc.employee.service.repository.impl;

import htcc.common.entity.jpa.EmployeeInfo;
import htcc.employee.service.repository.EmployeePermissionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeePermissionRepositoryImpl implements EmployeePermissionRepository {

    // TODO : Implement this class
    @Override
    public boolean canManageOffice(EmployeeInfo actor, String officeId) {
        return true;
    }

    @Override
    public boolean canManageEmployee(String actor, EmployeeInfo target) {
        return true;
    }
}
