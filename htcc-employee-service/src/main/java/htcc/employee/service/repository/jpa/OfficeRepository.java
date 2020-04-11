package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Office.Key>{

    List<Office> findByCompanyId(String companyId);
}
