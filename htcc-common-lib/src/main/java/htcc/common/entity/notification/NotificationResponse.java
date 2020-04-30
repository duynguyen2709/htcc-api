package htcc.common.entity.notification;

import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@ApiModel(description = "Thông tin notification")
public class NotificationResponse {

    private static final long serialVersionUID = 996270983005150708L;

    @ApiModelProperty(notes = "Id noti",
                      example = "abcxyz")
    @NotEmpty
    public String notiId = "";

    @ApiModelProperty(notes = "Id của màn hình cần navigate tới",
                      example = "0")
    public int screenId = 0;

    @ApiModelProperty(notes = "Title của noti (in đậm)",
                      example = "Thông báo về bảng lương")
    @NotEmpty
    public String title = "";

    @ApiModelProperty(notes = "Nội dung của noti",
                      example = "Bảng lương tháng 4 của bạn đã sẵn sàng. Vào kiểm tra ngay thôi !")
    @NotEmpty
    public String content = "";

    @ApiModelProperty(notes = "Ngày gửi noti (dd/MM/yyyy)",
                      example = "30/04/2020")
    @NotEmpty
    public String date = "";

    @ApiModelProperty(notes = "Giờ gửi noti (HH:mm)",
                      example = "12:00")
    @NotEmpty
    public String time = "";

    @ApiModelProperty(notes = "Id của icon hiển thị bên trái",
                      example = "icon1")
    @NotEmpty
    public String iconId = "";

    @ApiModelProperty(notes = "URL của icon")
    @NotEmpty
    public String iconUrl = "";

    @ApiModelProperty(notes = "Trạng thái đã đọc của noti")
    @NotEmpty
    public boolean hasRead = false;

    public NotificationResponse(NotificationModel model){
        this.notiId = model.notiId;
        this.screenId = model.screenId;
        this.title = model.title;
        this.content = model.content;
        this.date = DateTimeUtil.parseTimestampToString(model.sendTime, "dd/MM/yyyy");
        this.time = DateTimeUtil.parseTimestampToString(model.sendTime, "HH:mm");
        this.iconId = model.iconId;
        this.iconUrl = model.iconUrl;
        this.hasRead = model.hasRead;
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();
        map.put("notiId", this.notiId);
        map.put("screenId", this.screenId + "");
        map.put("title", this.title);
        map.put("content", this.notiId);
        map.put("date", this.date);
        map.put("time", this.time);
        map.put("iconId", this.iconId + "");
        map.put("iconUrl", this.iconUrl);
        map.put("hasRead", this.hasRead ? "true" : "false");
        return map;
    }
}
