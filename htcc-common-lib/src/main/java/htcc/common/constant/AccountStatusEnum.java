package htcc.common.constant;

import java.util.HashMap;

public enum AccountStatusEnum {

    ACTIVE(1),
    BLOCK (0)

    ;

    private final int value;

    private static final HashMap<Integer, AccountStatusEnum> map = new HashMap<>();

    AccountStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static AccountStatusEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        AccountStatusEnum[] var0 = values();

        for (AccountStatusEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
