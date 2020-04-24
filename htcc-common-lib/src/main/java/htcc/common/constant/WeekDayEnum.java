package htcc.common.constant;

import java.util.HashMap;

public enum WeekDayEnum {
    DEFAULT(0),
    SUNDAY(1),
    MONDAY (2),
    TUESDAY (3),
    WEDNESDAY (4),
    THURSDAY (5),
    FRIDAY (6),
    SATURDAY (7),
    ;

    private final int value;

    private static final HashMap<Integer, WeekDayEnum> map = new HashMap<>();

    WeekDayEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WeekDayEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        WeekDayEnum[] var0 = values();

        for (WeekDayEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
