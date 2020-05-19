package htcc.common.entity.icon;

import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class IconResponse implements Serializable {

    private static final long serialVersionUID = 13269111150807L;

    public List<NotificationIconConfig> iconList = new ArrayList<>();

    public List<ScreenInfo> screenList = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScreenInfo implements Serializable {

        private static final long serialVersionUID = 13269122250807L;

        public int screenId = 0;
        public String screenDescription = StringUtil.EMPTY;
    }
}
