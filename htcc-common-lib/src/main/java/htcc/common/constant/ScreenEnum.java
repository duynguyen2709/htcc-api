package htcc.common.constant;

import java.util.HashMap;

public enum ScreenEnum {

    DEFAULT (0, "Mặc định"),
    CHECKIN(1, "Màn hình Điểm danh"),
    DAY_OFF(2, "Màn hình Nghỉ phép"),
    STATISTICS(3, "Màn hình Thống kê"),
    PERSONAL_INFO(4, "Màn hình Thông tin cá nhân"),
    CONTACT_LIST(5, "Màn hình Danh bạ"),
    PAYCHECK(6, "Màn hình Bảng lương"),
    COMPLAINT(7, "Màn hình Khiếu nại"),
    ;

    private final int value;
    private final String screenDescription;

    private static final HashMap<Integer, ScreenEnum> map = new HashMap<>();

    ScreenEnum(int value, String screenDescription) {
        this.value = value;
        this.screenDescription = screenDescription;
    }

    public int getValue() {
        return this.value;
    }

    public String getScreenDescription() {
        return this.screenDescription;
    }

    public static ScreenEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ScreenEnum[] var0 = values();

        for (ScreenEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }
    }
}
