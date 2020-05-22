package htcc.common.entity.notification;

import htcc.common.entity.base.BaseLogEntity;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class NotificationLogEntity extends BaseLogEntity {

    private static final String TABLE_NAME = "NotificationLog";

    public String notiId    = "";
    public int    sourceClientId  = 1;
    public int    targetClientId  = 1;
    public int    receiverType  = 1;
    public String sender = "";
    public String companyId = "";
    public String officeId = "";
    public String username  = "";
    public long   sendTime  = 0L;
    public long   retryTime = 0L;
    public String title     = "";
    public String content   = "";
    public String iconId    = "";
    public String iconUrl   = "";
    public int    screenId  = 1;
    public int    status    = 1;
    public int    hasRead   = 0;
    public String tokenPush = "";

    public NotificationLogEntity(NotificationModel model) {
        this.requestId = model.requestId;
        this.notiId = model.notiId;
        this.sourceClientId = model.sourceClientId;
        this.targetClientId = model.targetClientId;
        this.receiverType = model.receiverType;
        this.sender = model.sender;
        this.companyId = model.companyId;
        this.officeId = model.officeId;
        this.username = model.username;
        this.sendTime = model.sendTime;
        this.retryTime = model.retryTime;
        this.title = model.title;
        this.content = model.content;
        this.iconId = model.iconId;
        this.iconUrl = model.iconUrl;
        this.screenId = model.screenId;
        this.status = model.status;
        this.hasRead = (model.hasRead ? 1 : 0);
        this.tokenPush = StringUtil.toJsonString(model.tokenPush);
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("requestId", this.requestId);
        params.put("notiId", this.notiId);
        params.put("sourceClientId", this.sourceClientId);
        params.put("targetClientId", this.targetClientId);
        params.put("receiverType", this.receiverType);
        params.put("sender", this.sender);
        params.put("companyId", this.companyId);
        params.put("officeId", this.officeId);
        params.put("username", this.username);
        params.put("sendTime", this.sendTime);
        params.put("retryTime", this.retryTime);
        params.put("title", this.title);
        params.put("content", this.content);
        params.put("iconId", this.iconId);
        params.put("iconUrl", this.iconUrl);
        params.put("screenId", this.screenId);
        params.put("tokenPush", this.tokenPush);
        params.put("status", this.status);
        params.put("hasRead", this.hasRead);
        return params;
    }

    @Override
    public long getCreateTime() {
        return this.sendTime;
    }

    @Override
    public String retrieveTableName() {
        return TABLE_NAME;
    }
}
