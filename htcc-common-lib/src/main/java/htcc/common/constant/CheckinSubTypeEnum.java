package htcc.common.constant;

import java.util.HashMap;

public enum CheckinSubTypeEnum {
    LOCATION(1),
    IMAGE (2),
    QR_CODE (3),
    FORM (4),

    ;

    private final int value;

    private static final HashMap<Integer, CheckinSubTypeEnum> map = new HashMap<>();

    CheckinSubTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CheckinSubTypeEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        CheckinSubTypeEnum[] var0 = values();

        for (CheckinSubTypeEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
