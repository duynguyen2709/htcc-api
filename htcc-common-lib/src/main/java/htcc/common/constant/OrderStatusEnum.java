package htcc.common.constant;

import java.util.HashMap;

public enum OrderStatusEnum {

    CREATED(4),
    DELIVERING(3),
    PAY_SUCCESS(2),
    SUCCESS(1),
    FAILED (0),

    ;

    private final int value;

    private static final HashMap<Integer, OrderStatusEnum> map = new HashMap<>();

    OrderStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static OrderStatusEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        OrderStatusEnum[] var0 = values();

        for (OrderStatusEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }
    }
}
