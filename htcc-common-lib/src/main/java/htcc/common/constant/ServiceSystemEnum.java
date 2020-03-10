package htcc.common.constant;

import java.util.HashMap;

public enum ServiceSystemEnum {
    ERROR(0),
    GATEWAY(1),
    EMPLOYEE(2),
    MANAGER (3),
    LOG(4),
    ADMIN(5)

    ;

    private final int value;

    private static final HashMap<Integer, ServiceSystemEnum> map = new HashMap<>();

    ServiceSystemEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ServiceSystemEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ServiceSystemEnum[] var0 = values();

        for (ServiceSystemEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }
    }

    public static int getServiceFromUri(String path){
        try {
            final String subpath = path.split("/")[2].toUpperCase();
            for (ServiceSystemEnum e : map.values()) {
                if (subpath.equals(e.toString())) {
                    return e.value;
                }
            }
        } catch (Exception ignored) {}
        return 0;
    }
}
