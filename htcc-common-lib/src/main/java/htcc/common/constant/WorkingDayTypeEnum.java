package htcc.common.constant;

import java.util.HashMap;

public enum WorkingDayTypeEnum {

    NORMAL(1),
    SPECIAL (2)

    ;

    private final int value;

    private static final HashMap<Integer, WorkingDayTypeEnum> map = new HashMap<>();

    WorkingDayTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WorkingDayTypeEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        WorkingDayTypeEnum[] var0 = values();

        for (WorkingDayTypeEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
