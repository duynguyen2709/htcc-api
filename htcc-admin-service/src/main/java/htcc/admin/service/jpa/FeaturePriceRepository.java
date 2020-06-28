package htcc.admin.service.jpa;

import htcc.common.entity.feature.FeaturePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturePriceRepository extends JpaRepository<FeaturePrice, String> {
}
