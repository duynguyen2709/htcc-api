package htcc.employee.service.component.hazelcast;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.HazelcastService;
import htcc.common.constant.CacheKeyEnum;
import htcc.common.constant.Constant;
import htcc.common.constant.SessionEnum;
import htcc.common.constant.WorkingDayTypeEnum;
import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.BuzConfig;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.jpa.Department;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.shift.FixedShiftArrangement;
import htcc.common.entity.shift.ShiftArrangementTemplate;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.entity.workingday.WorkingDay;
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

    @Autowired
    private ShiftTimeRepository shiftTimeRepository;

    @Autowired
    private FixedShiftArrangementRepository fixedShiftArrangementRepository;

    @Autowired
    private ShiftArrangementTemplateRepository shiftArrangementTemplateRepository;

    @PostConstruct
    public void loadAllStaticMap() throws Exception {
        log.info("####### Started Loading Static Config Map ########\n");

        loadCompanyMap();

        loadOfficeMap();

        loadDepartmentMap();

        // must be below company map to traverse
        loadCompanyDayOffInfoMap();

        loadWorkingDayMap();

        loadShiftTimeMap();

        loadFixedShiftArrangementMap();

        loadShiftArrangementTemplateMap();

        log.info("####### Loaded All Static Config Map Done ########\n");
    }

    public void loadShiftArrangementTemplateMap() {
        Map<String, List<ShiftArrangementTemplate>> map = new HashMap<>();

        shiftArrangementTemplateRepository.findAll().forEach(c -> {
            String key = c.getCompanyId();
            if (!map.containsKey(key) || map.get(key) == null) {
                map.put(key, new ArrayList<>());
            }

            map.get(key).add(c);
        });

        SHIFT_TEMPLATE_MAP = hazelcastService.reload(map, CacheKeyEnum.SHIFT_TEMPLATE);
        log.info("[loadShiftArrangementTemplateMap] SHIFT_TEMPLATE_MAP loaded succeed [{}]",
                StringUtil.toJsonString(SHIFT_TEMPLATE_MAP));
    }

    public void loadFixedShiftArrangementMap() {
        Map<String, List<FixedShiftArrangement>> map = new HashMap<>();

        fixedShiftArrangementRepository.findAll().forEach(c -> {
            String key = c.getCompanyId();
            if (!map.containsKey(key) || map.get(key) == null) {
                map.put(key, new ArrayList<>());
            }

            map.get(key).add(c);
        });

        FIXED_SHIFT_MAP = hazelcastService.reload(map, CacheKeyEnum.FIXED_SHIFT);
        log.info("[loadFixedShiftArrangementMap] FIXED_SHIFT_MAP loaded succeed [{}]",
                StringUtil.toJsonString(FIXED_SHIFT_MAP));

    }

    public void loadShiftTimeMap() {
        Map<String, List<ShiftTime>> map = new HashMap<>();

        shiftTimeRepository.findAll().forEach(c -> {
            String key = String.format("%s_%s", c.getCompanyId(), c.getOfficeId());
            if (!map.containsKey(key) || map.get(key) == null) {
                map.put(key, new ArrayList<>());
            }

            map.get(key).add(c);
        });

        SHIFT_TIME_MAP = hazelcastService.reload(map, CacheKeyEnum.SHIFT_TIME);
        log.info("[loadShiftTimeMap] SHIFT_TIME_MAP loaded succeed [{}]", StringUtil.toJsonString(SHIFT_TIME_MAP));

    }

    public void loadWorkingDayMap() throws Exception {
        Map<String, List<WorkingDay>> map = new HashMap<>();

        workingDayRepository.findAll().forEach(c -> {
                    String key = String.format("%s_%s", c.getCompanyId(), c.getOfficeId());
                    if (!map.containsKey(key) || map.get(key) == null) {
                        map.put(key, new ArrayList<>());
                    }

                    map.get(key).add(c);
                });

        for (List<WorkingDay> workingDays : map.values()){
            // validate logic to reject duplicate here
            Map<String, WorkingDay> temp = new HashMap<>();
            for (WorkingDay day : workingDays) {
                if (day.getType() == WorkingDayTypeEnum.SPECIAL.getValue()){
                    continue;
                }

                String key = String.format("%s_%s", day.getWeekDay(), day.getSession());
                if (temp.containsKey(key)){
                    throw new Exception(String.format("[WORKING_DAY_MAP] [%s - %s] already contains key [%s]",
                            day.getCompanyId(), day.getOfficeId(), key));
                }

                if (day.getSession() == SessionEnum.MORNING.getValue() ||
                    day.getSession() == SessionEnum.AFTERNOON.getValue()){
                    String subKey = String.format("%s_0", day.getWeekDay());
                    if (temp.containsKey(subKey)){
                        throw new Exception(String.format("[WORKING_DAY_MAP] [%s - %s] already contains full day config for week day [%s]",
                                day.getCompanyId(), day.getOfficeId(), day.getWeekDay()));
                    }
                }

                temp.put(key, day);
            }
        }

        WORKING_DAY_MAP = hazelcastService.reload(map, CacheKeyEnum.WORKING_DAY);
        log.info("[loadWorkingDayMap] WORKING_DAY_MAP loaded succeed [{}]", StringUtil.toJsonString(WORKING_DAY_MAP));
    }

    public void loadCompanyMap() {
        Map<String, Company> map = new HashMap<>();

        companyRepository.findAll().forEach(c -> map.put(c.getCompanyId(), c));

        COMPANY_MAP = hazelcastService.reload(map, CacheKeyEnum.COMPANY);
        log.info("[loadCompanyMap] COMPANY_MAP loaded succeed [{}]", StringUtil.toJsonString(COMPANY_MAP));
    }

    public void loadOfficeMap(){
        Map<String, Office> map = new HashMap<>();

        officeRepository.findAll().forEach(c -> map.put(c.getCompanyId() + "_" + c.getOfficeId(), c));

        OFFICE_MAP = hazelcastService.reload(map, CacheKeyEnum.OFFICE);
        log.info("[loadOfficeMap] OFFICE_MAP loaded succeed [{}]", StringUtil.toJsonString(OFFICE_MAP));
    }

    public void loadDepartmentMap() {
        Map<String, Department> map = new HashMap<>();

        departmentRepository.findAll().forEach(c -> map.put(c.getCompanyId() + "_" + c.getDepartment(), c));

        DEPARTMENT_MAP = hazelcastService.reload(map, CacheKeyEnum.DEPARTMENT);
        log.info("[loadDepartmentMap] DEPARTMENT_MAP loaded succeed [{}]", StringUtil.toJsonString(DEPARTMENT_MAP));
    }

    public void loadCompanyDayOffInfoMap() throws Exception {
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
