package htcc.employee.service.repository.jpa;

import htcc.common.entity.shift.ShiftTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftTimeRepository extends JpaRepository<ShiftTime, ShiftTime.Key> {
}
