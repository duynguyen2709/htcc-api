package htcc.employee.service.service.salary;

import htcc.common.entity.payslip.SalaryFormula;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.repository.jpa.SalaryFormulaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class SalaryFormulaService extends BaseJPAService<SalaryFormula, SalaryFormula.Key> {

    @Autowired
    private SalaryFormulaRepository repo;

    @Override
    public List<SalaryFormula> findAll() {
        return repo.findAll();
    }

    @Override
    public SalaryFormula findById(SalaryFormula.Key key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public SalaryFormula create(SalaryFormula salaryFormula) {
        return repo.save(salaryFormula);
    }

    @Override
    public SalaryFormula update(SalaryFormula salaryFormula) {
        return create(salaryFormula);
    }

    @Override
    public void delete(SalaryFormula.Key key) {
        repo.deleteById(key);
    }
}
