package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingDayRepository extends JpaRepository<WorkingDay, Integer> {
}
