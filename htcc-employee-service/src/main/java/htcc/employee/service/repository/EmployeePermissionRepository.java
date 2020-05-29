package htcc.employee.service.repository;

import htcc.common.entity.jpa.BuzConfig;
import htcc.common.entity.jpa.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeePermissionRepository {

    boolean canManageOffice(String companyId, String actor, String officeId);

    boolean canManageEmployee(String companyId, String actor, String targetEmployee);

    boolean isSuperAdmin(String companyId, String username);

    List<String> getCanManageOffices(String companyId, String username);

    List<EmployeeInfo> getCanManageEmployees(String companyId, String username);
}
