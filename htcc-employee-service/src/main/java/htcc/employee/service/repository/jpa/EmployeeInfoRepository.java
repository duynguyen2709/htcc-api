package htcc.employee.service.repository.jpa;

import htcc.employee.service.entity.jpa.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, EmployeeInfo.Key> {
}
