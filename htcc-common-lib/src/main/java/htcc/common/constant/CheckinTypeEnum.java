package htcc.common.constant;

import java.util.HashMap;

public enum CheckinTypeEnum {
    CHECKIN(1),
    CHECKOUT (2),

    ;

    private final int value;

    private static final HashMap<Integer, CheckinTypeEnum> map = new HashMap<>();

    CheckinTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CheckinTypeEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        CheckinTypeEnum[] var0 = values();

        for (CheckinTypeEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
