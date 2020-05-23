package htcc.employee.service.controller.checkin;

import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.checkin.qrcode.QrCodeCheckinEntity;
import htcc.employee.service.config.SecurityConfig;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "API tạo QR điểm danh cho công ty")
@RestController
@Log4j2
public class GenerateQrCodeController {

    @Autowired
    private SecurityConfig securityConfig;

    @GetMapping("/public/genqrcode")
    public BaseResponse generateQrCode(@RequestParam String companyId, @RequestParam String officeId,
                                       @RequestParam(required = false) String sig) {
        BaseResponse<QrCodeCheckinEntity> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!validateSig(companyId, officeId, sig)) {
                response = new BaseResponse<>(ReturnCodeEnum.PERMISSION_DENIED);
                return response;
            }

            long now = System.currentTimeMillis();
            String requestId = LoggingConfiguration.getTraceId();

            QrCodeCheckinEntity entity = new QrCodeCheckinEntity();
            entity.setCompanyId(companyId);
            entity.setOfficeId(officeId);
            entity.setGenTime(now);
            entity.setQrCodeId(String.format("%s-%s-%s-%s", companyId, officeId, now, requestId));
            response.setData(entity);
        } catch (Exception e) {
            log.error("[generateQrCode] companyId = [{}], officeId = [{}], sig = [{}]",
                    companyId, officeId, sig, e);
            response = new BaseResponse<>(e);
        }

        return response;
    }

    private boolean validateSig(String companyId, String officeId, String sig) {
        if (!securityConfig.isEnableQrGenSecurity()) {
            return true;
        }

        return false;
    }
}
