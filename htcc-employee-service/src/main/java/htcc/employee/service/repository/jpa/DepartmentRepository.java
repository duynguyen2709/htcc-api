package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Department.Key> {
}
