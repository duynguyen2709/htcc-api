package htcc.employee.service.repository.jpa;

import htcc.common.entity.payslip.SalaryFormula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryFormulaRepository extends JpaRepository<SalaryFormula, SalaryFormula.Key> {
}
