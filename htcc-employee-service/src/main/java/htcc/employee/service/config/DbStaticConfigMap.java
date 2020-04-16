package htcc.employee.service.config;

import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.BuzConfig;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.jpa.Department;
import htcc.common.entity.jpa.Office;

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
}
