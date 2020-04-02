package htcc.common.constant;

import java.util.HashMap;

public enum ReturnCodeEnum {

    INIT(2, "Đang xử lý"),
    SUCCESS(1, "Thành công"),
    EXCEPTION(0, "Hệ thống đang có lỗi. Vui lòng thử lại sau"),
    UNAUTHORIZE(401, "Xác thực token thất bại"),
    PERMISSION_DENIED(403, "Không có quyền truy cập"),
    WRONG_USERNAME_OR_PASSWORD(-1, "Sai tên đăng nhập hoặc mật khẩu"),
    ACCOUNT_LOCKED(-2, "Tài khoản đã bị khóa"),

    PARAM_CLIENTID_INVALID(-3, "Thiếu ClientID"),
    PARAM_DATA_INVALID(-4, "Dữ liệu không hợp lệ"),

    USER_NOT_FOUND(-5, "Không tìm thấy người dùng"),
    OLD_PASSWORD_NOT_MATCH(-6, "Mật khẩu cũ không khớp"),
    NEW_PASSWORD_MUST_DIFFER_FROM_OLD_PASSWORD(-7, "Mật khẩu mới không được trùng với mật khẩu cũ"),
    TOKEN_EXPIRED(-8, "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại"),
    CHECKIN_ALREADY(-9, "Đã thực hiện điểm danh vào hôm nay"),
    CHECKOUT_ALREADY(-10, "Đã thực hiện điểm danh ra hôm nay"),
    NOT_CHECKIN(-11, "Chưa thực hiện điểm danh vào hôm nay"),
    CHECKIN_TIME_NOT_VALID(-12, "Thời gian điểm danh không hợp lệ"),
    LOG_NOT_FOUND(-13, "Không tìm thấy dữ liệu"),
    DATE_WRONG_FORMAT(-14, "Định dạng ngày tháng không hợp lệ"),
    MAXIMUM_FILES_EXCEED(-15, "Chỉ cho upload tối đa 3 ảnh"),
    MAXIMUM_FILE_SIZE_EXCEED(-16, "Chỉ cho upload tối đa ảnh tối đa 10MB"),
    DATA_NOT_FOUND(-17, "Không tìm thấy dữ liệu"),
    DATA_ALREADY_EXISTED(-18, "Dữ liệu đã tồn tại"),
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
        return map.getOrDefault(iValue, EXCEPTION);
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
