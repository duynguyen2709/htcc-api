package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, EmployeeInfo.Key> {

    List<EmployeeInfo> findByCompanyId(String companyId);

    List<EmployeeInfo> findByCompanyIdAndOfficeId(String companyId, String officeId);
}
