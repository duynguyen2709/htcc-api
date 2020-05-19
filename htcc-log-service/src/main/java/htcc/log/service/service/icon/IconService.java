package htcc.log.service.service.icon;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.ScreenEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.util.StringUtil;
import htcc.log.service.service.LogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class IconService {

    @Autowired
    private LogService logService;

    private Map<String, NotificationIconConfig> notiIconMap = null;

    public void setIconInfo(NotificationModel model) {
        try {
            String iconId = model.getIconId();
            if (StringUtil.isEmpty(iconId)) {
                iconId = Constant.DEFAULT_ICON_ID;
            }

            if (notiIconMap == null || !notiIconMap.containsKey(iconId)) {
                notiIconMap = getListIconsFromAdminService();
                log.info("[getListIconsFromAdminService] return {}", StringUtil.toJsonString(notiIconMap));
            }

            NotificationIconConfig config = null;
            if (notiIconMap == null || !notiIconMap.containsKey(iconId)) {
                config = new NotificationIconConfig();
                config.setScreenId(ScreenEnum.DEFAULT.getValue());
                config.setIconId(iconId);
                config.setIconURL(Constant.DEFAULT_ICON_URL);
            } else {
                config = notiIconMap.get(iconId);
            }

            model.setIconId(config.getIconId());
            model.setIconUrl(config.getIconURL());
            model.setScreenId(config.getScreenId());

        } catch (Exception e) {
            log.error("[setIconInfo] ex", e);
        }
    }

    private Map<String, NotificationIconConfig> getListIconsFromAdminService() {
        try {
            BaseResponse res = logService.getListIcons();

            if (res == null || res.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue() ||
                    res.getData() == null) {
                throw new Exception("logService.getListIcons response = " + StringUtil.toJsonString(res));
            }

            String data = StringUtil.toJsonString(res.data);

            return StringUtil.json2Collection(data, new TypeToken<Map<String, NotificationIconConfig>>(){}.getType());
        } catch (Exception e){
            log.error("[getListIcons] ex", e);
            return null;
        }
    }
}
