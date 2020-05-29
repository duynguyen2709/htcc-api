package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.BuzConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuzConfigRepository extends JpaRepository<BuzConfig, BuzConfig.Key> {

    List<BuzConfig> findByCompanyIdAndSection(String companyId, String section);
}
