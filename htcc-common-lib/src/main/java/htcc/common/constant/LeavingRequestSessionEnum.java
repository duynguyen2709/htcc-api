package htcc.common.constant;

import java.util.HashMap;

public enum LeavingRequestSessionEnum {

    AFTERNOON(2),
    MORNING(1),
    FULL_DAY (0)
    ;

    private final int value;

    private static final HashMap<Integer, LeavingRequestSessionEnum> map = new HashMap<>();

    LeavingRequestSessionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static LeavingRequestSessionEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        LeavingRequestSessionEnum[] var0 = values();

        for (LeavingRequestSessionEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
