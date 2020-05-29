package htcc.employee.service.repository.jpa;

import htcc.common.entity.workingday.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingDayRepository extends JpaRepository<WorkingDay, Integer> {
}
