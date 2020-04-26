package htcc.employee.service.controller.notification;

import htcc.common.comparator.EmployeeIdComparator;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.notification.NotificationResponse;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "API Get list notification")
@RestController
@Log4j2
public class NotificationController {

    @Autowired
    private NotificationService notiService;

    @ApiOperation(value = "Lấy danh sách notification", response = NotificationResponse.class)
    @GetMapping("/notifications/{companyId}/{username}")
    public BaseResponse getListNotification(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                       @PathVariable String companyId,
                                       @ApiParam(name = "username", value = "[Path] Tên đăng nhập", defaultValue = "admin", required = true)
                                       @PathVariable String username,
                                       @ApiParam(name = "index", value = "[QueryString] Phân trang (0,1,2...)", defaultValue = "0", required = false)
                                       @RequestParam(required = true, defaultValue = "0") Integer index,
                                       @ApiParam(name = "size", value = "[QueryString] Số record mỗi trang, nếu không gửi sẽ lấy default 20," +
                                               " nếu khác 0 cần gửi thêm index để lấy trang tiếp theo",
                                                 defaultValue = "0", required = false)
                                       @RequestParam(required = false, defaultValue = "0") Integer size) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            if (size == null || size == 0){
                size = 20;
            }

            int startIndex = size * index;

            List<NotificationResponse> notificationList = notiService
                    .getListNotification(companyId, username, startIndex, size)
                    .stream()
                    .map(NotificationResponse::new)
                    .collect(Collectors.toList());

            response.setData(notificationList);
        } catch (Exception e){
            log.error(String.format("[getListNotification] [%s-%s-%s-%s]",
                    companyId, username, index, size), e);
            response = new BaseResponse(e);
        }
        return response;
    }
}
