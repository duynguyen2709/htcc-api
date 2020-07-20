package htcc.employee.service.repository.jpa;

import htcc.common.entity.role.ManagerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRoleRepository extends JpaRepository<ManagerRole, ManagerRole.Key> {
}
