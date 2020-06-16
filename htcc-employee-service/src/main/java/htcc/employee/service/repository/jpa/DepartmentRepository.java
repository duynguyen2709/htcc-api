package htcc.employee.service.repository.jpa;

import htcc.common.entity.jpa.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Department.Key> {
}
