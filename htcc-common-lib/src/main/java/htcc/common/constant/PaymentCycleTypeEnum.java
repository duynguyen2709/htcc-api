package htcc.common.constant;

import java.util.HashMap;

public enum PaymentCycleTypeEnum {

    MONTHLY(1),
    YEARLY (2)
    ;

    private final int value;

    private static final HashMap<Integer, PaymentCycleTypeEnum> map = new HashMap<>();

    PaymentCycleTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static PaymentCycleTypeEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        PaymentCycleTypeEnum[] var0 = values();

        for (PaymentCycleTypeEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
