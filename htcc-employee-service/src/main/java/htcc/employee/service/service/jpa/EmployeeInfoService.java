package htcc.employee.service.service.jpa;

import htcc.common.service.BaseJPAService;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.employee.service.repository.jpa.EmployeeInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class EmployeeInfoService extends BaseJPAService<EmployeeInfo, EmployeeInfo.Key> {

    @Autowired
    private EmployeeInfoRepository repo;

    @Autowired
    private EntityManager em;

    @Override
    public List<EmployeeInfo> findAll() {
        return repo.findAll();
    }

    public List<EmployeeInfo> findByFullTextSearch(String searchValue) {
        List<EmployeeInfo> result = new ArrayList<>();
        try {
            String nameValue = getSearchValue(searchValue);
            String employeeIdValue = "%" + searchValue + "%";

            String query = String.format("SELECT * FROM EmployeeInfo WHERE (MATCH(employeeId,fullName) AGAINST ('%s' IN BOOLEAN MODE)) " +
                    "OR (employeeId LIKE '%s')", nameValue, employeeIdValue);
            return em.createNativeQuery(query, EmployeeInfo.class).getResultList();
        } catch (Exception e) {
            log.error("[findByFullTextSearch] [{}]", searchValue, e);
        }
        return result;
    }

    private String getSearchValue(String searchValue) {
        List<String> values = new ArrayList<>();
        if (searchValue.contains(" ")){
            values = Arrays.asList(searchValue.split(" "));
        } else {
            values = Collections.singletonList(searchValue);
        }

        String search = "";
        for (String val : values) {
            search += " +" + val;
        }
        search = search.substring(1) + "*";
        return search;
    }

    public List<EmployeeInfo> findByCompanyId(String companyId) {
        return repo.findByCompanyId(companyId);
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
