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

    public int    clientId  = 1;
    public String companyId = "";
    public String username  = "";
    public String notiId    = "";
    public long   sendTime  = 0L;
    public long   retryTime = 0L;
    public String title     = "";
    public String content   = "";
    public String iconId    = "";
    public String iconUrl   = "";
    public int    status    = 1;
    public String tokenPush = "";

    public NotificationLogEntity(NotificationModel model) {
        this.requestId = model.requestId;
        this.clientId = model.clientId;
        this.companyId = model.companyId;
        this.username = model.username;
        this.notiId = model.notiId;
        this.sendTime = model.sendTime;
        this.retryTime = model.retryTime;
        this.title = model.title;
        this.content = model.content;
        this.iconId = model.iconId;
        this.iconUrl = model.iconUrl;
        this.status = model.status;
        this.tokenPush = StringUtil.toJsonString(model.tokenPush);
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("requestId", this.requestId);
        params.put("clientId", this.clientId);
        params.put("companyId", this.companyId);
        params.put("username", this.username);
        params.put("notiId", this.notiId);
        params.put("sendTime", this.sendTime);
        params.put("retryTime", this.retryTime);
        params.put("title", this.title);
        params.put("content", this.content);
        params.put("iconId", this.iconId);
        params.put("iconUrl", this.iconUrl);
        params.put("tokenPush", this.tokenPush);
        params.put("status", this.status);
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
