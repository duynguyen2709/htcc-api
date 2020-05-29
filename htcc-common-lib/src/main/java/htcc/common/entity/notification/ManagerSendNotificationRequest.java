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
public class ManagerSendNotificationRequest extends BaseJPAEntity {

    private static final long serialVersionUID = 9862183005150709L;

    // 0 - ALL , 1 - COMPANY, 2 - SPECIFIC USER, 3 - OFFICE
    @ApiModelProperty(notes = "Đối tượng nhận (1: Công ty, 2: Nhân viên cụ thể, 3: chi nhánh)", example = "1")
    public int receiverType = 1;

    @ApiModelProperty(notes = "Người gửi (username/tên admin)", example = "admin")
    public String sender = "";

    @ApiModelProperty(notes = "Mã công ty", example = "HCMUS")
    public String companyId = "";

    @ApiModelProperty(notes = "Mã chi nhánh (khi receiverType = 3)", example = "NVC")
    public String officeId = "";

    @ApiModelProperty(notes = "Tên người nhận (khi receiverType = 2)", example = "duyna")
    public String username = "";

    @ApiModelProperty(notes = "Tiêu đề của noti",
                      example = "Thông báo về bảng lương")
    public String title = "";

    @ApiModelProperty(notes = "Nội dung của noti",
                      example = "Bảng lương tháng 4 của bạn đã sẵn sàng. Vào kiểm tra ngay thôi !")
    public String content = "";

    @ApiModelProperty(notes = "Id của icon hiển thị tương ứng",
                      example = "checkin")
    public String iconId = "";

    @Override
    public String isValid() {

        if (NotificationReceiverSystemEnum.fromInt(receiverType) == null ||
                receiverType == NotificationReceiverSystemEnum.ALL.getValue()) {
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

        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (receiverType == NotificationReceiverSystemEnum.OFFICE.getValue()) {
            if (StringUtil.isEmpty(officeId)) {
                return "Mã chi nhánh không được rỗng";
            }
        }

        if (receiverType == NotificationReceiverSystemEnum.USER.getValue()) {
            if (StringUtil.isEmpty(username)) {
                return "Tên người nhận không được rỗng";
            }
        }

        return StringUtil.EMPTY;
    }
}
