package htcc.log.service.service.icon;

import htcc.common.constant.Constant;
import htcc.common.constant.ScreenEnum;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class IconService {

    private Map<String, NotificationIconConfig> notiIconMap = new HashMap<>();

    @PostConstruct
    public void initIconMap() {
        notiIconMap = new HashMap<>();

        NotificationIconConfig icon = new NotificationIconConfig();
        icon.setScreenId(ScreenEnum.DEFAULT.getValue());
        icon.setIconId(Constant.DEFAULT_ICON_ID);
        icon.setIconURL(Constant.DEFAULT_ICON_URL);

        notiIconMap.put(icon.getIconId(), icon);
    }

    public void setIconInfo(NotificationModel model) {
        try {
            String iconId = model.getIconId();
            if (StringUtil.isEmpty(iconId)) {
                iconId = Constant.DEFAULT_ICON_ID;
            }

            NotificationIconConfig config = notiIconMap.getOrDefault(iconId,
                    notiIconMap.get(Constant.DEFAULT_ICON_ID));

            model.setIconId(config.getIconId());
            model.setIconUrl(config.getIconURL());
            model.setScreenId(config.getScreenId());

        } catch (Exception e) {
            log.error("[setIconInfo] ex", e);
        }
    }

    public void setNotiIconMap(Map<String, NotificationIconConfig> map) {
        if (map == null || map.isEmpty()) {
            this.initIconMap();
            return;
        }

        if (!map.containsKey(Constant.DEFAULT_ICON_ID)) {
            NotificationIconConfig icon = new NotificationIconConfig();
            icon.setScreenId(ScreenEnum.DEFAULT.getValue());
            icon.setIconId(Constant.DEFAULT_ICON_ID);
            icon.setIconURL(Constant.DEFAULT_ICON_URL);

            map.put(icon.getIconId(), icon);
        }

        this.notiIconMap = map;
    }
}
