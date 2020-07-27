package htcc.common.constant;

import java.util.HashMap;

public enum SalaryFormulaEnum {
    TOTAL_BASE_SALARY("TOTAL_BASE_SALARY", "Tổng lương cố định"),
    BASE ("BASE", "Lương cơ bản"),
    EXTRA("EXTRA", "Thưởng"),
    MEAL("MEAL", "Tiền ăn"),
    OVERTIME("OVERTIME", "Làm việc ngoài giờ"),
    LATE("LATE", "Đi trễ"),
    NON_PERMISSION_OFF("NON_PERMISSION_OFF", "Nghỉ không phép"),
    TAX("TAX", "Thuế TNCN"),
    INSURANCE("INSURANCE", "Bảo hiểm"),
    PRE_TAX_REDUCTION("PRE_TAX_REDUCTION", "Giảm trừ phụ thuộc"),
    ;

    private final String id;
    private final String description;

    private static final HashMap<String, SalaryFormulaEnum> map = new HashMap<>();

    SalaryFormulaEnum(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public static SalaryFormulaEnum getEnum(String iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        SalaryFormulaEnum[] var0 = values();

        for (SalaryFormulaEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.id, errorCodeEnum);
        }
    }
}
