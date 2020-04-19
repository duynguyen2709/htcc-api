package htcc.employee.service.service.jpa;

import htcc.common.entity.jpa.Company;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class CompanyService extends BaseJPAService<Company, String> {

    @Autowired
    private CompanyRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<Company> findAll() {
        return new ArrayList<>(DbStaticConfigMap.COMPANY_MAP.values());
    }

    @Override
    public Company findById(String key) {
        return DbStaticConfigMap.COMPANY_MAP.getOrDefault(key, null);
    }

    @Override
    public Company create(Company company) {
        Company newCompany = repo.save(company);
        hazelcastLoader.loadCompanyMap();
        return company;
    }

    @Override
    public Company update(Company company) {
        return create(company);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
        hazelcastLoader.loadCompanyMap();
    }
}
