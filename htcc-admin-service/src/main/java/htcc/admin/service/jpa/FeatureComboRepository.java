package htcc.admin.service.jpa;

import htcc.common.entity.feature.FeatureCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureComboRepository extends JpaRepository<FeatureCombo, String> {
}
