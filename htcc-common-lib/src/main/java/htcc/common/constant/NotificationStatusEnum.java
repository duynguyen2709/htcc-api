package htcc.common.constant;

import java.util.HashMap;

public enum NotificationStatusEnum {

    FAILED_RETRYING(4),
    PENDING(3),
    INIT(2),
    SUCCESS(1),
    FAILED (0)
    ;

    private final int value;

    private static final HashMap<Integer, NotificationStatusEnum> map = new HashMap<>();

    NotificationStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static NotificationStatusEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        NotificationStatusEnum[] var0 = values();

        for (NotificationStatusEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
