package htcc.employee.service.service.jpa;

import htcc.common.entity.jpa.Company;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.repository.jpa.CompanyRepository;
import htcc.employee.service.repository.jpa.EmployeeInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class CompanyService extends BaseJPAService<Company, String> {

    @Autowired
    private CompanyRepository repo;

    @Override
    public List<Company> findAll() {
        return repo.findAll();
    }

    @Override
    public Company findById(String key) {
        Optional<Company> company = repo.findById(key);
        return company.orElse(null);
    }

    @Override
    public Company create(Company company) {
        return repo.save(company);
    }

    @Override
    public Company update(Company company) {
        return repo.save(company);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
    }
}
