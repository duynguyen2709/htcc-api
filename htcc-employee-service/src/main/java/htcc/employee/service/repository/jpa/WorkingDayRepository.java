package htcc.employee.service.repository.jpa;

import htcc.common.entity.workingday.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Integer> {
}
