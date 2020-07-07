package htcc.admin.service.controller.feature;

import htcc.admin.service.service.jpa.FeatureComboService;
import htcc.common.constant.FeatureEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.feature.FeatureCombo;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "API quản lý gói combo tính năng",
     description = "API quản lý danh sách gói combo tính năng")
@RestController
@Log4j2
public class FeatureComboController {

    @Autowired
    private FeatureComboService featureComboService;

    @ApiOperation(value = "Lấy danh sách gói combo tính năng", response = FeatureCombo.class)
    @GetMapping("/combos")
    public BaseResponse getListFeatureCombo() {
        BaseResponse<List<FeatureCombo>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<FeatureCombo> dataResponse = featureComboService.findAll();
            response.setData(dataResponse);
            return response;
        } catch (Exception e){
            log.error("[getListFeatureCombo] ex", e);
            return new BaseResponse<>(e);
        }
    }


    @ApiOperation(value = "Xóa gói combo", response = BaseResponse.class)
    @DeleteMapping("/combos/{comboId}")
    public BaseResponse deleteCombo(@PathVariable String comboId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            FeatureCombo entity = featureComboService.findById(comboId);
            if (entity == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy gói combo " + comboId);
                return response;
            }

            featureComboService.delete(comboId);
        } catch (Exception e){
            log.error("[deleteCombo] id = {} ex", comboId, e);
            response = new BaseResponse<>(e);
        }

        return response;
    }

    @ApiOperation(value = "Thêm combo mới", response = FeatureCombo.class)
    @PostMapping("/combos")
    public BaseResponse createCombo(@RequestBody FeatureCombo request) {
        BaseResponse<FeatureCombo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Thêm combo mới thành công");

        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            error = validateComboDetail(request.getComboDetail());
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            response.setData(featureComboService.create(request));
        } catch (Exception e){
            log.error("[createCombo] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    @ApiOperation(value = "Cập nhật combo", response = FeatureCombo.class)
    @PutMapping("/combos/{comboId}")
    public BaseResponse updateCombo(@PathVariable String comboId, @RequestBody FeatureCombo request) {
        BaseResponse<FeatureCombo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật thành công");
        try {
            request.setComboId(comboId);
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            error = validateComboDetail(request.getComboDetail());
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            FeatureCombo entity = featureComboService.findById(comboId);
            if (entity == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy combo " + comboId);
                return response;
            }

            response.setData(featureComboService.update(request));
        } catch (Exception e){
            log.error("[updateCombo] id = {} ex", comboId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private String validateComboDetail(Map<String, Object> comboDetail) {
        try {
            for (FeatureEnum featureEnum : FeatureEnum.values()) {
                if (!comboDetail.containsKey(featureEnum.getValue())) {
                    return "Thiếu thông tin tính năng " + featureEnum.getValue() + " trong gói";
                }

                if (!featureEnum.getValue().equals(FeatureEnum.EMPLOYEE_MANAGE.getValue())) {
                    boolean isActive = Boolean.parseBoolean(StringUtil.valueOf(comboDetail.get(featureEnum.getValue())));
                }
            }

            Object value = comboDetail.get(FeatureEnum.EMPLOYEE_MANAGE.getValue());
            int numEmployees = (int)(Float.parseFloat(String.valueOf(value)));
            if (numEmployees <= 0) {
                return "Số nhân viên trong gói phải lớn hơn 0";
            }
            comboDetail.replace(FeatureEnum.EMPLOYEE_MANAGE.getValue(), numEmployees);
        } catch (Exception e) {
            log.error("[validateComboDetail] {} ex", StringUtil.toJsonString(comboDetail), e);
            return "Thông tin trong gói không hợp lệ";
        }
        return StringUtil.EMPTY;
    }
}
