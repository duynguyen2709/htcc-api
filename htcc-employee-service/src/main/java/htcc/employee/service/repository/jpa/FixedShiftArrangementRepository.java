package htcc.employee.service.repository.jpa;

import htcc.common.entity.shift.FixedShiftArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedShiftArrangementRepository extends JpaRepository<FixedShiftArrangement, Integer> {
}
