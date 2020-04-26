package htcc.common.entity.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class NotificationModel {

    private static final long serialVersionUID = 986270983005150708L;

    public String       requestId = "";
    public int          clientId  = 1;
    public String       companyId = "";
    public String       username  = "";
    public String       notiId    = "";
    public long         sentTime  = 0L;
    public String       title     = "";
    public String       content   = "";
    public String       iconId    = "";
    public String       iconUrl   = "";
    public List<String> tokenPush = new ArrayList<>();
}
