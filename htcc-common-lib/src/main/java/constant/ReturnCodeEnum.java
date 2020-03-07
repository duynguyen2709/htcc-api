package constant;

import java.util.HashMap;

public enum ReturnCodeEnum {

    INIT(2, "Đang xử lý"),
    SUCCESS(1, "Thành công"),
    EXCEPTION(0, "Hệ thống đang có lỗi. Vui lòng thử lại sau"),
    UNAUTHORIZE(401, "Không có quyền truy cập"),
    PERMISSION_DENIED(403, ""),
    WRONG_USERNAME_OR_PASSWORD(-1, "Sai tên đăng nhập hoặc mật khẩu"),
    ACCOUNT_LOCKED(-2, "Tài khoản đã bị khóa"),

    PARAM_CLIENTID_INVALID(-3, "Thiếu ClientID"),
    PARAM_DATA_INVALID(-4, "Dữ liệu không hợp lệ"),

    ;

    private final int value;
    private final String message;

    private static final HashMap<Integer, ReturnCodeEnum> map = new HashMap<>();

    ReturnCodeEnum(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static ReturnCodeEnum fromInt(int iValue) {
        return map.get(iValue);
    }

    public String toString() {
        return this.name();
    }

    static {
        ReturnCodeEnum[] var0 = values();

        for (ReturnCodeEnum errorCodeEnum : var0) {
            map.put(errorCodeEnum.value, errorCodeEnum);
        }

    }
}
