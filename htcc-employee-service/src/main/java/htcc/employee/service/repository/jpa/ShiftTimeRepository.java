package htcc.employee.service.repository.jpa;

import htcc.common.entity.shift.ShiftTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftTimeRepository extends JpaRepository<ShiftTime, ShiftTime.Key> {
}
