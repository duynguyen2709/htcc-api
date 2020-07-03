package htcc.admin.service.controller.feature;

import htcc.admin.service.service.jpa.FeatureComboService;
import htcc.admin.service.service.jpa.FeaturePriceService;
import htcc.common.constant.FeatureEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.feature.FeatureCombo;
import htcc.common.entity.feature.FeaturePrice;
import htcc.common.entity.feature.GetFeaturePriceResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/internal")
@ApiIgnore
public class InternalFeatureController {

    @Autowired
    private FeaturePriceService featurePriceService;

    @Autowired
    private FeatureComboService featureComboService;

    @GetMapping("/features")
    public BaseResponse internalGetFeaturePrice() {
        BaseResponse<GetFeaturePriceResponse> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            GetFeaturePriceResponse dataResponse = new GetFeaturePriceResponse();
            dataResponse.setFeatureList(featurePriceService.findAll());
            dataResponse.setComboList(featureComboService.findAll());

            calculateTotalPrice(dataResponse);
            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[internalGetFeaturePrice] ex", e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void calculateTotalPrice(GetFeaturePriceResponse data) throws Exception {
        List<FeatureCombo> comboList = data.getComboList();
        List<FeaturePrice> featurePriceList = data.getFeatureList();

        for (FeatureCombo combo : comboList) {
            Map<String, Object> detail = combo.getComboDetail();
            int numEmployees = (int)(Float.parseFloat(String.valueOf(detail.get(FeatureEnum.EMPLOYEE_MANAGE.getValue()))));
            long totalPrice = 0L;
            for (Map.Entry<String, Object> entry : detail.entrySet()) {
                FeaturePrice featurePrice = getFeature(featurePriceList, entry.getKey());
                if (featurePrice == null) {
                    throw new Exception("Feature " + entry.getKey() + " not found");
                }

                if (entry.getKey().equals(FeatureEnum.EMPLOYEE_MANAGE.getValue())) {
                    totalPrice += featurePrice.getUnitPrice() * numEmployees;
                }
                else {
                    boolean isActive = Boolean.parseBoolean(String.valueOf(entry.getValue()));
                    if (isActive) {
                        if (featurePrice.getCalcByEachEmployee() == 1) {
                            totalPrice += featurePrice.getUnitPrice() * numEmployees;
                        }
                        else {
                            totalPrice += featurePrice.getUnitPrice();
                        }
                    }
                }
            }

            totalPrice -= (long)(totalPrice * combo.getDiscountPercentage() / 100);
            combo.setTotalPrice(totalPrice);
        }
    }

    private FeaturePrice getFeature(List<FeaturePrice> list, String featureId) {
        return list.stream()
                .filter(c -> c.getFeatureId().equals(featureId))
                .findFirst()
                .orElse(null);
    }
}
