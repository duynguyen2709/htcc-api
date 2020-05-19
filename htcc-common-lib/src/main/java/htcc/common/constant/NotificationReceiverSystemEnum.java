package htcc.common.constant;

import java.util.HashMap;

public enum NotificationReceiverSystemEnum {
    ALL(0),
    COMPANY(1),
    USER (2),
    OFFICE(3),

    ;

    private final int value;

    private static final HashMap<Integer, NotificationReceiverSystemEnum> map = new HashMap<>();

    NotificationReceiverSystemEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static NotificationReceiverSystemEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        NotificationReceiverSystemEnum[] var0 = values();

        for (NotificationReceiverSystemEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
