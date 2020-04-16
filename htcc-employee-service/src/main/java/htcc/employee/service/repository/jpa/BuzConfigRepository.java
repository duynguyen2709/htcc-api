package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.BuzConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuzConfigRepository extends JpaRepository<BuzConfig, BuzConfig.Key> {

    List<BuzConfig> findByCompanyIdAndSection(String companyId, String section);
}
