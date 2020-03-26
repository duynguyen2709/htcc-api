package htcc.common.constant;

import java.util.HashMap;

public enum ComplaintStatusEnum {

    PROCESSING(2),
    DONE(1),
    REJECTED (0)

    ;

    private final int value;

    private static final HashMap<Integer, ComplaintStatusEnum> map = new HashMap<>();

    ComplaintStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ComplaintStatusEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ComplaintStatusEnum[] var0 = values();

        for (ComplaintStatusEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
