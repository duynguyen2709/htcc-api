package htcc.employee.service.config;

import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.*;
import htcc.common.entity.shift.FixedShiftArrangement;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.entity.workingday.WorkingDay;

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

    // key = companyId
    // List Fixed Shift for 1 company
    public static Map<String, List<FixedShiftArrangement>> FIXED_SHIFT_MAP = new HashMap<>();
}
