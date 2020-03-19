package htcc.employee.service.service.jpa;

import htcc.common.service.BaseJPAService;
import htcc.employee.service.entity.jpa.EmployeeInfo;
import htcc.employee.service.repository.jpa.EmployeeInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class EmployeeInfoService extends BaseJPAService<EmployeeInfo, EmployeeInfo.Key> {

    @Autowired
    private EmployeeInfoRepository repo;

    @Override
    public List<EmployeeInfo> findAll() {
        return repo.findAll();
    }

    @Override
    public EmployeeInfo findById(EmployeeInfo.Key key) {
        Optional<EmployeeInfo> user = repo.findById(key);
        return user.orElse(null);
    }

    @Override
    public EmployeeInfo create(EmployeeInfo employeeInfo) {
        return repo.save(employeeInfo);
    }

    @Override
    public EmployeeInfo update(EmployeeInfo employeeInfo) {
        return repo.save(employeeInfo);
    }

    @Override
    public void delete(EmployeeInfo.Key key) {
        repo.deleteById(key);
    }
}
