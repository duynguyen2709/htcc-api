package htcc.employee.service.config;

import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbStaticConfigMap {

    public static Map<String, Company> COMPANY_MAP = new HashMap<>();

    public static Map<String, Office> OFFICE_MAP = new HashMap<>();

    public static Map<String, Department> DEPARTMENT_MAP = new HashMap<>();

    public static Map<String, BuzConfig> BUZ_CONFIG_MAP = new HashMap<>();

    public static Map<String, CompanyDayOffInfo> COMPANY_DAY_OFF_INFO_MAP = new HashMap<>();

    public static Map<String, List<WorkingDay>> WORKING_DAY_MAP = new HashMap<>();

    public static Map<String, List<ShiftTime>> SHIFT_TIME_MAP = new HashMap<>();

    public static List<Office> findOfficeByCompanyId(String companyId){
        return OFFICE_MAP.values()
                .stream()
                .filter(o -> o.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    public static List<Department> findDepartmentByCompanyId(String companyId){
        return DEPARTMENT_MAP.values()
                .stream()
                .filter(o -> o.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    public static Office findHeadquarter(String companyId){
        return OFFICE_MAP.values()
                .stream()
                .filter(o -> o.getCompanyId().equals(companyId) && o.isHeadquarter)
                .findFirst().orElse(null);
    }
}
