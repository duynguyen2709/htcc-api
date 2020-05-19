package htcc.admin.service.controller;

import htcc.admin.service.service.jpa.NotificationIconConfigService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.ScreenEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.icon.IconResponse;
import htcc.common.entity.icon.NotificationIconConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Log4j2
public class InternalNotificationIconConfigController {

    @Autowired
    private NotificationIconConfigService notiIconService;

    @GetMapping("/internal/icons")
    public BaseResponse getListIcon() {
        BaseResponse<Map<String, NotificationIconConfig>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<NotificationIconConfig> iconList = notiIconService.findAll();
            Map<String, NotificationIconConfig> dataResponse = new HashMap<>();
            iconList.forEach(c -> dataResponse.put(c.getIconId(), c));
            response.setData(dataResponse);
            return response;
        } catch (Exception e){
            log.error("[internal/getListIcon] ex", e);
            return new BaseResponse<>(e);
        }
    }
}
