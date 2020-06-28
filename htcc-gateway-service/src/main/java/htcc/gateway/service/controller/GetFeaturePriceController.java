package htcc.gateway.service.controller;

import htcc.common.component.kafka.KafkaProducerService;
import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.base.BaseUser;
import htcc.common.entity.feature.GetFeaturePriceResponse;
import htcc.common.util.StringUtil;
import htcc.gateway.service.entity.request.ResetPasswordRequest;
import htcc.gateway.service.service.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Api(tags = "API public để lấy giá tính năng")
@RestController
@Log4j2
@RequestMapping("/public")
public class GetFeaturePriceController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/features")
    public BaseResponse getFeaturePrice() {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String baseURL = String.format("http://%s/internal/features", Constant.HTCC_ADMIN_SERVICE);
            BaseResponse subResponse = restTemplate.getForObject(baseURL, BaseResponse.class);
            if (subResponse == null || subResponse.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()) {
                throw new Exception("call Admin Service return " + StringUtil.toJsonString(subResponse));
            }
            response.setData(subResponse.getData());
        } catch (Exception e) {
            log.error("[getFeaturePrice] ex", e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
