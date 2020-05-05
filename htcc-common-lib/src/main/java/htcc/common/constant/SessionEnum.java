package htcc.common.constant;

import java.util.HashMap;

public enum SessionEnum {

//    NIGHT(3),
    AFTERNOON(2),
    MORNING(1),
    FULL_DAY (0)
    ;

    private final int value;

    private static final HashMap<Integer, SessionEnum> map = new HashMap<>();

    SessionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SessionEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        SessionEnum[] var0 = values();

        for (SessionEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
