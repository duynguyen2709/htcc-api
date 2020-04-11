package htcc.employee.service.service.jpa;

import htcc.common.entity.jpa.Office;
import htcc.common.service.BaseJPAService;
import htcc.employee.service.repository.jpa.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeService extends BaseJPAService<Office, Office.Key> {

    @Autowired
    private OfficeRepository repo;

    @Override
    public List<Office> findAll() {
        return repo.findAll();
    }

    public List<Office> findByCompanyId(String companyId) {
        return repo.findByCompanyId(companyId);
    }

    @Override
    public Office findById(Office.Key key) {
        Optional<Office> office = repo.findById(key);
        return office.orElse(null);
    }

    @Override
    public Office create(Office office) {
        return repo.save(office);
    }

    @Override public Office update(Office office) {
        return repo.save(office);
    }

    @Override
    public void delete(Office.Key key) {
        repo.deleteById(key);
    }
}
