package htcc.common.constant;

import java.util.HashMap;

public enum SalaryFormulaTypeEnum {

    PERCENTAGE(2),
    DIRECT(1),
    TIMES(0),

    ;

    private final int value;

    private static final HashMap<Integer, SalaryFormulaTypeEnum> map = new HashMap<>();

    SalaryFormulaTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SalaryFormulaTypeEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        SalaryFormulaTypeEnum[] var0 = values();

        for (SalaryFormulaTypeEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }
    }
}
