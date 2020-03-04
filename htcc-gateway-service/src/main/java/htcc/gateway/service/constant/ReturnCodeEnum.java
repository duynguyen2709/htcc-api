package htcc.gateway.service.constant;

import java.util.HashMap;

public enum ReturnCodeEnum {

    INIT(2),
    SUCCESS(1),
    EXCEPTION(0),
    UNAUTHORIZE(401),
    WRONG_USERNAME_OR_PASSWORD(-1),
    ACCOUNT_LOCKED(-2),

    PARAM_CLIENTID_INVALID(-3),
    PARAM_REQDATE_INVALID(-4),
    PARAM_SIG_INVALID(-5),
    PARAM_DATA_INVALID(-6),

    CHECK_SIG_NOT_MATCH(-7),
    TIME_LIMIT_EXCEED(-8),
    REPLAY_ATTACK_BLOCKED(-9),

    ;

    private final int value;

    private static final HashMap<Integer, ReturnCodeEnum> returnMap = new HashMap<>();

    ReturnCodeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ReturnCodeEnum fromInt(int iValue) {
        return returnMap.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ReturnCodeEnum[] var0 = values();

        for (ReturnCodeEnum errorCodeEnum : var0) {
            returnMap.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
