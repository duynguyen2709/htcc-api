package htcc.employee.service.repository.jpa;

import htcc.common.entity.role.EmployeePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePermissionRepository extends JpaRepository<EmployeePermission, EmployeePermission.Key> {
}
