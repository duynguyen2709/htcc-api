package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Office.Key>{

    List<Office> findByCompanyId(String companyId);
}
