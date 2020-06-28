package htcc.admin.service.controller.feature;

import htcc.admin.service.service.jpa.FeaturePriceService;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.feature.FeaturePrice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "API quản lý tính năng",
     description = "API quản lý danh sách các tính năng hệ thống hỗ trợ")
@RestController
@Log4j2
public class FeatureController {

    @Autowired
    private FeaturePriceService featurePriceService;

    @ApiOperation(value = "Lấy danh sách tính năng", response = FeaturePrice.class)
    @GetMapping("/features")
    public BaseResponse getListFeatures() {
        BaseResponse<List<FeaturePrice>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<FeaturePrice> dataResponse = featurePriceService.findAll();
            response.setData(dataResponse);
            return response;
        } catch (Exception e){
            log.error("[getListFeatures] ex", e);
            return new BaseResponse<>(e);
        }
    }


    @ApiOperation(value = "Cập nhật tính năng", response = FeaturePrice.class)
    @PutMapping("/features/{featureId}")
    public BaseResponse updateFeature(@PathVariable String featureId, @RequestBody FeaturePrice request) {
        BaseResponse<FeaturePrice> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            FeaturePrice entity = featurePriceService.findById(featureId);
            if (entity == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage("Không tìm thấy tính năng " + featureId);
                return response;
            }

            request.setFeatureId(featureId);
            request.setLinkedScreen(entity.getLinkedScreen());
            request.setDisplayScreen(entity.getDisplayScreen());

            response.setData(featurePriceService.update(request));
        } catch (Exception e){
            log.error("[updateFeature] id = [{}] ex", featureId, e);
            response = new BaseResponse<>(e);
        }

        return response;
    }
}
