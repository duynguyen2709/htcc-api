package htcc.employee.service.component.hazelcast;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.HazelcastService;
import htcc.common.constant.CacheKeyEnum;
import htcc.common.constant.Constant;
import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.*;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.jpa.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static htcc.employee.service.config.DbStaticConfigMap.*;

@Component
@Log4j2
public class HazelcastLoader {

    @Autowired
    private HazelcastService hazelcastService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private BuzConfigRepository buzConfigRepository;

    @Autowired
    private WorkingDayRepository workingDayRepository;

    @PostConstruct
    public void loadAllStaticMap() throws Exception {
        log.info("####### Started Loading Static Config Map ########\n");

        loadCompanyMap();

        loadOfficeMap();

        loadDepartmentMap();

        // must be below company map to traverse
        loadCompanyDayOffInfoMap();

        loadWorkingDayMap();

        log.info("####### Loaded All Static Config Map Done ########\n");
    }

    public void loadWorkingDayMap() {
        if (WORKING_DAY_MAP != null) {
            WORKING_DAY_MAP.clear();
            WORKING_DAY_MAP = null;
        }

        Map<String, List<WorkingDay>> map = new HashMap<>();

        workingDayRepository.findAll().forEach(c -> {
                    String key = String.format("%s_%s", c.getCompanyId(), c.getOfficeId());
                    if (!map.containsKey(key) || map.get(key) == null) {
                        map.put(key, new ArrayList<>());
                    }

                    map.get(key).add(c);
                });

        WORKING_DAY_MAP = hazelcastService.reload(map, CacheKeyEnum.WORKING_DAY);
        log.info("[loadCompanyMap] WORKING_DAY_MAP loaded succeed [{}]", StringUtil.toJsonString(WORKING_DAY_MAP));
    }

    public void loadCompanyMap() {
        if (COMPANY_MAP != null) {
            COMPANY_MAP.clear();
            COMPANY_MAP = null;
        }

        Map<String, Company> map = new HashMap<>();

        companyRepository.findAll().forEach(c -> map.put(c.getCompanyId(), c));

        COMPANY_MAP = hazelcastService.reload(map, CacheKeyEnum.COMPANY);
        log.info("[loadCompanyMap] COMPANY_MAP loaded succeed [{}]", StringUtil.toJsonString(COMPANY_MAP));
    }

    public void loadOfficeMap(){
        if (OFFICE_MAP != null) {
            OFFICE_MAP.clear();
            OFFICE_MAP = null;
        }

        Map<String, Office> map = new HashMap<>();

        officeRepository.findAll().forEach(c -> map.put(c.getCompanyId() + "_" + c.getOfficeId(), c));

        OFFICE_MAP = hazelcastService.reload(map, CacheKeyEnum.OFFICE);
        log.info("[loadOfficeMap] OFFICE_MAP loaded succeed [{}]", StringUtil.toJsonString(OFFICE_MAP));
    }

    public void loadDepartmentMap() {
        if (DEPARTMENT_MAP != null) {
            DEPARTMENT_MAP.clear();
            DEPARTMENT_MAP = null;
        }

        Map<String, Department> map = new HashMap<>();

        departmentRepository.findAll().forEach(c -> map.put(c.getCompanyId() + "_" + c.getDepartment(), c));

        DEPARTMENT_MAP = hazelcastService.reload(map, CacheKeyEnum.DEPARTMENT);
        log.info("[loadDepartmentMap] DEPARTMENT_MAP loaded succeed [{}]", StringUtil.toJsonString(DEPARTMENT_MAP));
    }

    public void loadCompanyDayOffInfoMap() throws Exception {
        if (COMPANY_DAY_OFF_INFO_MAP != null) {
            COMPANY_DAY_OFF_INFO_MAP.clear();
            COMPANY_DAY_OFF_INFO_MAP = null;
        }

        Map<String, CompanyDayOffInfo> map = new HashMap<>();

        for (String companyId : COMPANY_MAP.keySet()) {

            List<BuzConfig> configs = buzConfigRepository.findByCompanyIdAndSection(companyId, Constant.DAY_OFF_INFO);
            if (configs.isEmpty()) {
                throw new Exception(String.format("Find BuzConfig for company [%s] return empty", companyId));
            }
            CompanyDayOffInfo info = new CompanyDayOffInfo();

            boolean allowCancelRequestValue = Boolean.parseBoolean(findByKey(configs, Constant.ALLOW_CANCEL_REQUEST));

            int maxDayAllowCancelValue  = Integer.parseInt(findByKey(configs, Constant.MAX_DAY_ALLOW_CANCEL));

            List<CompanyDayOffInfo.CategoryEntity> categoryListValue =
                    StringUtil.json2Collection(findByKey(configs, Constant.CATEGORY_LIST),
                            new TypeToken<List<CompanyDayOffInfo.CategoryEntity>>() {}.getType());

            List<CompanyDayOffInfo.DayOffByLevelEntity> dayOffByLevelValue =
                    StringUtil.json2Collection(findByKey(configs, Constant.DAY_OFF_BY_LEVEL),
                            new TypeToken<List<CompanyDayOffInfo.DayOffByLevelEntity>>() {}.getType());

            info.setAllowCancelRequest(allowCancelRequestValue);
            info.setMaxDayAllowCancel(maxDayAllowCancelValue);
            info.setCategoryList(categoryListValue);
            info.setDayOffByLevel(dayOffByLevelValue);

            map.put(companyId, info);
        }

        COMPANY_DAY_OFF_INFO_MAP = hazelcastService.reload(map, CacheKeyEnum.COMPANY_DAY_OFF);
        log.info("[loadCompanyDayOffInfoMap] COMPANY_DAY_OFF_INFO_MAP loaded succeed [{}]", StringUtil.toJsonString(COMPANY_DAY_OFF_INFO_MAP));
    }

    public void loadBuzConfigMap() {
    }

    private String findByKey(List<BuzConfig> list, String key) {
        for (BuzConfig config : list) {
            if (config.getKey().equals(key)) {
                return config.getValue();
            }
        }

        return StringUtil.EMPTY;
    }

}
