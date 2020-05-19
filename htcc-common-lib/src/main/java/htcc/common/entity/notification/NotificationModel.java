package htcc.common.entity.notification;

import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.NotificationStatusEnum;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class NotificationModel {

    private static final long serialVersionUID = 986270983005150708L;

    // TODO : get screenId, icon, tokenPush from config
    // notiId must have format "yyyyMM..."

    public String       requestId = "";
    public String       notiId    = "";
    public int          sourceClientId  = 1;
    public int          targetClientId  = 1;
    public int          receiverType  = 0;
    public String       sender = "";
    public String       companyId = "";
    public String       officeId = "";
    public String       username  = "";
    public long         sendTime  = 0L;
    public long         retryTime = 0L;
    public String       title     = "";
    public String       content   = "";
    public String       iconId    = "";
    public String       iconUrl   = "";
    public int          screenId  = 0;
    public int          status    = 1;
    public boolean      hasRead   = false;
    public List<String> tokenPush = new ArrayList<>();

    public int numRetries = 0;

    public NotificationModel(NotificationLogEntity log){
        this.requestId = log.requestId;
        this.notiId = log.notiId;
        this.sourceClientId = log.sourceClientId;
        this.targetClientId = log.targetClientId;
        this.receiverType = log.receiverType;
        this.sender = log.sender;
        this.companyId = log.companyId;
        this.officeId = log.officeId;
        this.username = log.username;
        this.screenId = log.screenId;
        this.sendTime = log.sendTime;
        this.retryTime = log.retryTime;
        this.title = log.title;
        this.content = log.content;
        this.iconId = log.iconId;
        this.iconUrl = log.iconUrl;
        this.status = log.status;
        this.hasRead = (log.hasRead == 1);
        this.tokenPush = StringUtil.json2Collection(log.tokenPush, StringUtil.LIST_STRING_TYPE);
    }

    public NotificationModel(AdminSendNotificationRequest request) {
        this.requestId = LoggingConfiguration.getTraceId();
        this.sourceClientId = ClientSystemEnum.ADMIN_WEB.getValue();
        this.receiverType = request.getReceiverType();
        this.sender = request.getSender();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.iconId = request.getIconId();
        this.hasRead = false;
        this.status = NotificationStatusEnum.INIT.getValue();
    }
}
