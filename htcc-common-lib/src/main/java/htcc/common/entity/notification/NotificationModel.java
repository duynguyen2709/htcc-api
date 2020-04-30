package htcc.common.entity.notification;

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
    public int          clientId  = 1;
    public String       companyId = "";
    public String       username  = "";
    public String       notiId    = "";
    public int          screenId  = 0;
    public long         sendTime  = 0L;
    public long         retryTime = 0L;
    public String       title     = "";
    public String       content   = "";
    public String       iconId    = "";
    public String       iconUrl   = "";
    public int          status    = 1;
    public boolean      hasRead   = false;
    public List<String> tokenPush = new ArrayList<>();

    public int numRetries = 0;

    public NotificationModel(NotificationLogEntity log){
        this.requestId = log.requestId;
        this.clientId = log.clientId;
        this.companyId = log.companyId;
        this.username = log.username;
        this.notiId = log.notiId;
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
}
