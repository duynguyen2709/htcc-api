package htcc.employee.service.repository;

import htcc.common.entity.jpa.BuzConfig;
import htcc.common.entity.jpa.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeePermissionRepository {

    boolean canManageOffice(EmployeeInfo actor, String officeId);

    boolean canManageEmployee(String actor, EmployeeInfo target);
}
