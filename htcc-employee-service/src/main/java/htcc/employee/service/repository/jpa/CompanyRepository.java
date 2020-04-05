package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.Company;
import htcc.common.entity.jpa.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
}
