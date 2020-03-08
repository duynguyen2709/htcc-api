package htcc.common.constant;

import java.util.HashMap;

public enum ClientSystemEnum {
    EUREKA_DASHBOARD(0),
    MOBILE(1),
    MANAGER_WEB (2),
    ADMIN_WEB(3)

    ;

    private final int value;

    private static final HashMap<Integer, ClientSystemEnum> map = new HashMap<>();

    ClientSystemEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ClientSystemEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ClientSystemEnum[] var0 = values();

        for (ClientSystemEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
