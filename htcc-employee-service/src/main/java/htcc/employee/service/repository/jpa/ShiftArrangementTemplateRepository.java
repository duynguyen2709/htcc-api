package htcc.employee.service.repository.jpa;

import htcc.common.entity.shift.ShiftArrangementTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftArrangementTemplateRepository extends JpaRepository<ShiftArrangementTemplate, Integer> {
}
