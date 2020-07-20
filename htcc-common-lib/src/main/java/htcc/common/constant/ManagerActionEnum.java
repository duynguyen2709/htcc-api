package htcc.common.constant;

import java.util.HashMap;

public enum ManagerActionEnum {
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    READ("READ"),
    DELETE("DELETE"),
    ;

    private final String value;

    private static final HashMap<String, ManagerActionEnum> map = new HashMap<>();

    ManagerActionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ManagerActionEnum getEnum(String iValue) {
        return map.get(iValue);
    }

    static {
        ManagerActionEnum[] var0 = values();

        for (ManagerActionEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }
    }
}
