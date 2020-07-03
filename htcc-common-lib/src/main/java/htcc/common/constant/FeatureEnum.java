package htcc.common.constant;

import java.util.HashMap;

public enum FeatureEnum {
    CHECKIN("CHECKIN"),
    CHECKIN_IMAGE("CHECKIN_IMAGE"),
    CHECKIN_QR("CHECKIN_QR"),
    CHECKIN_FORM("CHECKIN_FORM"),
    COMPLAINT("COMPLAINT"),
    CONTACT_LIST("CONTACT_LIST"),
    DAY_OFF("DAY_OFF"),
    EMPLOYEE_MANAGE("EMPLOYEE_MANAGE"),
    NOTIFICATION("NOTIFICATION"),
    PAYCHECK("PAYCHECK"),
    PERSONAL_INFO("PERSONAL_INFO"),
    REPORT("REPORT"),
    STATISTICS("STATISTICS"),
    WORKING_DAY("WORKING_DAY"),
    ;

    private final String value;

    private static final HashMap<String, FeatureEnum> map = new HashMap<>();

    FeatureEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static FeatureEnum getEnum(String iValue) {
        return map.get(iValue);
    }

    static {
        FeatureEnum[] var0 = values();

        for (FeatureEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }
    }
}
