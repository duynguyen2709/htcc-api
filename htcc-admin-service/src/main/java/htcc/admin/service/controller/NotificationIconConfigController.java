package htcc.admin.service.controller;

import htcc.admin.service.service.GoogleDriveService;
import htcc.admin.service.service.jpa.NotificationIconConfigService;
import htcc.admin.service.service.rest.EmployeeCompanyService;
import htcc.admin.service.service.rest.GatewayCompanyUserService;
import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.ScreenEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.icon.IconResponse;
import htcc.common.entity.icon.NotificationIconConfig;
import htcc.common.entity.jpa.Company;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;

@Api(tags = "API quản lý icon",
     description = "API quản lý danh sách icon")
@RestController
@Log4j2
public class NotificationIconConfigController {

    @Autowired
    private NotificationIconConfigService notiIconService;

    @Autowired
    private GoogleDriveService driveService;

    @ApiOperation(value = "Lấy danh sách icon", response = IconResponse.class)
    @GetMapping("/icons")
    public BaseResponse getListIcon() {
        BaseResponse<IconResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            IconResponse dataResponse = new IconResponse();
            dataResponse.setIconList(notiIconService.findAll());
            dataResponse.setScreenList(new ArrayList<>());
            Arrays.stream(ScreenEnum.values()).forEach(c -> {
                dataResponse.getScreenList().add(new IconResponse.ScreenInfo(c.getValue(), c.getScreenDescription()));
            });
            response.setData(dataResponse);
            return response;
        } catch (Exception e){
            log.error("[getListIcon] ex", e);
            return new BaseResponse<>(e);
        }
    }


    @ApiOperation(value = "Xóa icon", response = BaseResponse.class)
    @DeleteMapping("/icons/{iconId}")
    public BaseResponse deleteIcon(@PathVariable String iconId) {
        BaseResponse<BaseResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            NotificationIconConfig entity = notiIconService.findById(iconId);
            if (entity == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy icon có id " + iconId);
                return response;
            }

            notiIconService.delete(iconId);

        } catch (Exception e){
            log.error("[deleteIcon] id = [{}] ex", iconId, e);
            response = new BaseResponse<>(e);
        }

        return response;
    }


    @ApiOperation(value = "Thêm icon mới", response = NotificationIconConfig.class)
    @PostMapping("/icons")
    public BaseResponse createIcon(@RequestParam String iconId,
                                   @ApiParam(value = "URL icon mới cần tạo", required = false)
                                   @RequestParam(required = false) String iconURL,
                                   @RequestParam int screenId,
                                   @ApiParam(value = "[Multipart/form-data] file icon mới cần tạo", required = false)
                                   @RequestParam(required = false) MultipartFile iconImage) {
        BaseResponse<NotificationIconConfig> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Thêm icon mới thành công");
        NotificationIconConfig entity = null;
        try {
            entity = new NotificationIconConfig(iconId, iconURL, screenId);
            if (iconImage != null) {
                String URL = driveService.uploadNotiIcon(iconImage, String.format("%s_%s", iconId, System.currentTimeMillis()));
                entity.setIconURL(URL);
            }

            String error = entity.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            entity = notiIconService.create(entity);
            response.setData(entity);

        } catch (Exception e){
            log.error("[createIcon] [{}] ex", StringUtil.toJsonString(entity), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }
}
