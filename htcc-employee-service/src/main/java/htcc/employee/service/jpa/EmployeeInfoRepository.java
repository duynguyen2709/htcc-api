package htcc.employee.service.jpa;

import htcc.employee.service.entity.jpa.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, EmployeeInfo.Key> {
}
