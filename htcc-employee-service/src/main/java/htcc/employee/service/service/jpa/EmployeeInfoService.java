package htcc.employee.service.service.jpa;

import htcc.common.constant.Constant;
import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.service.BaseJPAService;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.EmployeeInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.CompletableFuture;

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
            String nameValue       = getSearchValue(searchValue);
            String employeeIdValue = "%" + searchValue + "%";

            String query = String.format(
                    "SELECT * FROM EmployeeInfo WHERE (MATCH(employeeId,fullName) AGAINST ('%s' IN BOOLEAN MODE)) " +
                            "OR (employeeId LIKE '%s')", nameValue, employeeIdValue);
            return em.createNativeQuery(query, EmployeeInfo.class).getResultList();
        } catch (Exception e) {
            log.error("[findByFullTextSearch] [{}]", searchValue, e);
        }
        return result;
    }

    private String getSearchValue(String searchValue) {
        List<String> values = new ArrayList<>();
        if (searchValue.contains(" ")) {
            values = Arrays.asList(searchValue.split(" "));
        }
        else {
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

    public List<EmployeeInfo> findByCompanyIdAndOfficeId(String companyId, String officeId) {
        return repo.findByCompanyIdAndOfficeId(companyId, officeId);
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

    @Async("asyncExecutor")
    public CompletableFuture<Float> getTotalDayOff(String companyId, String username) {
        try {
            EmployeeInfo employee = findById(new EmployeeInfo.Key(companyId, username));
            if (employee == null) {
                throw new Exception("Employee not found !");
            }

            final float level = employee.getLevel();
            final List<CompanyDayOffInfo.DayOffByLevelEntity> dayOffByLevelList =
                    DbStaticConfigMap.COMPANY_DAY_OFF_INFO_MAP.get(companyId).getDayOffByLevel();

            float value = Constant.DEFAULT_TOTAL_DAY_OFF;
            Optional<CompanyDayOffInfo.DayOffByLevelEntity> optValue = dayOffByLevelList
                                                                        .stream()
                                                                        .filter(d -> d.getLevel() == level)
                                                                        .findFirst();

            int index = 0;
            if (!optValue.isPresent()) {
                // find nearest level
                for (int i = 0; i < dayOffByLevelList.size(); i++) {
                    if (dayOffByLevelList.get(i).getLevel() > level) {
                        index = i - 1;
                        break;
                    }
                }

                if (index < 0) {
                    throw new Exception(String.format("Find DayOffByLevelEntity company [%s], level [%s] return null",
                            companyId, level));
                }

                value = dayOffByLevelList.get(index).getTotalDayOff();
            } else {
                value = optValue.get().getTotalDayOff();
            }

            return CompletableFuture.completedFuture(value);
        } catch (Exception e) {
            log.error("[getTotalDayOff] [{} - {}] ex", companyId, username, e);
            return CompletableFuture.completedFuture(Constant.DEFAULT_TOTAL_DAY_OFF);
        }
    }

    public void deleteOffice(String companyId, String officeId) {
        try {
            List<EmployeeInfo> listEmployee = repo.findByCompanyIdAndOfficeId(companyId, officeId);
            for (EmployeeInfo employee : listEmployee) {
                employee.setOfficeId(StringUtil.EMPTY);
            }
            repo.saveAll(listEmployee);
        } catch (Exception e) {
            log.error(String.format("[deleteOffice] [%s-%s] ex", companyId, officeId), e);
        }
    }

    public void deleteDepartment(String companyId, String department) {
        try {
            List<EmployeeInfo> listEmployee = repo.findByCompanyIdAndDepartment(companyId, department);
            for (EmployeeInfo employee : listEmployee) {
                employee.setDepartment(StringUtil.EMPTY);
            }
            repo.saveAll(listEmployee);
        } catch (Exception e) {
            log.error(String.format("[deleteDepartment] [%s-%s] ex", companyId, department), e);
        }
    }
}
