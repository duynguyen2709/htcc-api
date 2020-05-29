package htcc.common.constant;

import java.util.HashMap;

public enum ShiftArrangementTypeEnum {

    FIXED(1),
    DAY(2)

    ;

    private final int value;

    private static final HashMap<Integer, ShiftArrangementTypeEnum> map = new HashMap<>();

    ShiftArrangementTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ShiftArrangementTypeEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ShiftArrangementTypeEnum[] var0 = values();

        for (ShiftArrangementTypeEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
