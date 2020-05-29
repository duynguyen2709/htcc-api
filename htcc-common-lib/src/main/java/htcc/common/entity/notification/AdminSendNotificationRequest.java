package htcc.common.entity.notification;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.NotificationReceiverSystemEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "Request gửi notification")
public class AdminSendNotificationRequest extends BaseJPAEntity {

    private static final long serialVersionUID = 9862183005150708L;

    // 0 - ALL , 1 - COMPANY, 2 - SPECIFIC USER
    @ApiModelProperty(notes = "Đối tượng nhận (0: Toàn hệ thống, 1: Công ty, 2: Người dùng cụ thể", example = "0")
    public int    receiverType   = 1;

    // 1 , 2 , 3
    @ApiModelProperty(notes = "Hệ thống nhận (1: Mobile, 2: Web quản lý, 3: Web admin", example = "1")
    public int    targetClientId = 1;

    @ApiModelProperty(notes = "Người gửi (username/tên admin)", example = "admin")
    public String sender         = "";

    @ApiModelProperty(notes = "Mã công ty (khi receiverType = 1 | 2", example = "HCMUS")
    public String companyId      = "";

    @ApiModelProperty(notes = "Tên người nhận (khi receiverType = 2", example = "duyna")
    public String username       = "";

    @ApiModelProperty(notes = "Title của noti (in đậm)",
                      example = "Thông báo về bảng lương")
    public String title          = "";

    @ApiModelProperty(notes = "Nội dung của noti",
                      example = "Bảng lương tháng 4 của bạn đã sẵn sàng. Vào kiểm tra ngay thôi !")
    public String content        = "";

    @ApiModelProperty(notes = "Id của icon hiển thị tương ứng",
                      example = "checkin")
    public String iconId         = "";

    @Override
    public String isValid() {
        if (ClientSystemEnum.fromInt(targetClientId) == null ||
                targetClientId == ClientSystemEnum.EUREKA_DASHBOARD.getValue()) {
            return "Hệ thống nhận không hợp lệ";
        }

        if (NotificationReceiverSystemEnum.fromInt(receiverType) == null ||
                receiverType == NotificationReceiverSystemEnum.OFFICE.getValue()) {
            return "Đối tượng nhận không hợp lệ";
        }

        if (StringUtil.isEmpty(iconId)) {
            return "Màn hình liên kết không được rỗng";
        }

        if (StringUtil.isEmpty(sender)) {
            return "Tên người gửi không được rỗng";
        }
        if (StringUtil.isEmpty(title)) {
            return "Tiêu đề thông báo không được rỗng";
        }

        if (StringUtil.isEmpty(content)) {
            return "Nội dung thông báo không được rỗng";
        }

        if (targetClientId == ClientSystemEnum.MOBILE.getValue() ||
                targetClientId == ClientSystemEnum.MANAGER_WEB.getValue()) {
            if (StringUtil.isEmpty(companyId)) {
                return "Mã công ty không được rỗng";
            }
        }

        if (receiverType == NotificationReceiverSystemEnum.COMPANY.getValue()) {
            if (StringUtil.isEmpty(companyId)) {
                return "Mã công ty không được rỗng";
            }
        }

        if (receiverType == NotificationReceiverSystemEnum.USER.getValue()) {
            if (targetClientId != ClientSystemEnum.ADMIN_WEB.getValue()) {
                if (StringUtil.isEmpty(companyId)) {
                    return "Mã công ty không được rỗng";
                }
            }

            if (StringUtil.isEmpty(username)) {
                return "Tên người nhận không được rỗng";
            }
        }

        return StringUtil.EMPTY;
    }
}
