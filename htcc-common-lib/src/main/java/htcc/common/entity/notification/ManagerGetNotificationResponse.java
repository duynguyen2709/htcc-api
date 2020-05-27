package htcc.common.entity.notification;

import htcc.common.constant.ScreenEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ManagerGetNotificationResponse implements Serializable {

    private static final long serialVersionUID = 987650983005150708L;

    public int    receiverType      = 0;
    public String sender            = "";
    public String officeId          = "";
    public String username          = "";
    public String fullName          = "";
    public long   sendTime          = 0L;
    public String title             = "";
    public String content           = "";
    public String iconId            = "";
    public String iconUrl           = "";
    public int    screenId          = 0;
    public String screenDescription = "";
    public int    status            = 1;

    public ManagerGetNotificationResponse(NotificationModel model) {
        this.receiverType = model.receiverType;
        this.sender = model.sender;
        this.officeId = model.officeId;
        this.username = model.username;
        this.screenId = model.screenId;
        this.sendTime = model.sendTime;
        this.title = model.title;
        this.content = model.content;
        this.iconId = model.iconId;
        this.iconUrl = model.iconUrl;
        this.status = model.status;
        this.screenDescription = ScreenEnum.fromInt(model.screenId).getScreenDescription();
    }

}
