package htcc.employee.service.config;

import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.BuzConfig;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.jpa.Office;

import java.util.HashMap;
import java.util.Map;

public class DbStaticConfigMap {

    public static Map<String, Company> COMPANY_MAP = new HashMap<>();

    public static Map<String, Office> OFFICE_MAP = new HashMap<>();

    public static Map<String, BuzConfig> BUZ_CONFIG_MAP = new HashMap<>();

    public static Map<String, CompanyDayOffInfo> COMPANY_DAY_OFF_INFO_MAP = new HashMap<>();
}
