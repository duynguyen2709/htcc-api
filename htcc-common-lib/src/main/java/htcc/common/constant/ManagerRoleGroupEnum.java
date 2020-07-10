package htcc.common.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum ManagerRoleGroupEnum {
    NOTIFICATION(1, "NOTIFICATION", Arrays.asList(ManagerActionEnum.READ,
            ManagerActionEnum.CREATE)),

    STATISTICS(3, "STATISTICS", Arrays.asList(ManagerActionEnum.READ)),

    COMPANY(1, "COMPANY", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.UPDATE)),

    OFFICE(1, "OFFICE", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.CREATE,
            ManagerActionEnum.DELETE, ManagerActionEnum.UPDATE)),

    DEPARTMENT(1, "DEPARTMENT", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.CREATE,
            ManagerActionEnum.DELETE, ManagerActionEnum.UPDATE)),

    COMPLAINT(7, "COMPLAINT", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.UPDATE)),

    LEAVING_REQUEST(2, "LEAVING_REQUEST", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.UPDATE)),

    CHECKIN(1, "CHECKIN", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.UPDATE)),

    EMPLOYEE_MANAGE(1, "EMPLOYEE_MANAGE", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.CREATE,
            ManagerActionEnum.DELETE, ManagerActionEnum.UPDATE)),

    DAY_OFF(2, "DAY_OFF", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.UPDATE)),

    WORKING_DAY(12, "WORKING_DAY", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.UPDATE)),

    SHIFT(12, "SHIFT", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.CREATE,
            ManagerActionEnum.DELETE, ManagerActionEnum.UPDATE)),

    SHIFT_TEMPLATE(12, "SHIFT_TEMPLATE", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.CREATE,
            ManagerActionEnum.DELETE)),

    SHIFT_ARRANGEMENT(12, "SHIFT_ARRANGEMENT", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.CREATE,
            ManagerActionEnum.DELETE)),

    SALARY(6, "SALARY", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.CREATE,
            ManagerActionEnum.DELETE, ManagerActionEnum.UPDATE)),

    PERMISSION(1, "PERMISSION", Arrays.asList(ManagerActionEnum.READ, ManagerActionEnum.CREATE,
            ManagerActionEnum.DELETE, ManagerActionEnum.UPDATE))
    ;

    private final int screenId;
    private final String group;
    private List<ManagerActionEnum> actions;

    private static final HashMap<String, ManagerRoleGroupEnum> map = new HashMap<>();

    ManagerRoleGroupEnum(int screenId, String group, List<ManagerActionEnum> actions) {
        this.screenId = screenId;
        this.group = group;
        this.actions = actions;
    }

    public List<ManagerActionEnum> getActions() {
        return this.actions;
    }

    public int getScreenId() {
        return this.screenId;
    }

    public String getRoleGroup() {
        return this.group;
    }

    public static ManagerRoleGroupEnum getEnum(String group) {
        return map.get(group);
    }

    public String toString() {
        return this.name();
    }

    static {
        ManagerRoleGroupEnum[] var0 = values();

        for (ManagerRoleGroupEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.group, errorCodeEnum);
        }
    }
}
