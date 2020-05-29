package htcc.employee.service.service.icon;

import htcc.common.constant.Constant;
import htcc.common.constant.ScreenEnum;
import htcc.common.entity.icon.NotificationIconConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<NotificationIconConfig> getListIcon() {
        List<NotificationIconConfig> iconList = new ArrayList<>(notiIconMap.values());
        iconList.forEach(c -> {
            String desc = ScreenEnum.fromInt(c.getScreenId()).getScreenDescription();
            c.setScreenDescription(desc);
        });

        return iconList;
    }

    public boolean containIcon(String iconId) {
        return notiIconMap.containsKey(iconId);
    }
}
