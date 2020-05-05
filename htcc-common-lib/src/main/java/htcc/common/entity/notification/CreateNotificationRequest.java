package htcc.common.entity.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @NoArgsConstructor public class CreateNotificationRequest implements Serializable {

    private static final long serialVersionUID = 98620983005150708L;

    // TODO : DELETE THIS CLASS AFTER TESTING

    public String companyId = "";
    public String username  = "";
    public int    screenId  = 0;
    public String click_action = "FLUTTER_NOTIFICATION_CLICK";
    public String title     = "";
    public String content   = "";
}
