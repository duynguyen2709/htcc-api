package htcc.web.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htcc.web.service.config.QrCodeConfig;
import htcc.web.service.entity.BaseResponse;
import htcc.web.service.enums.ReturnCodeEnum;
import htcc.web.service.service.QrCodeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

// TODO : Auto reload QR Code when checkin succeed
@RestController
@Log4j2
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;

    @Autowired
    private QrCodeConfig qrCodeConfig;

    @GetMapping(value = "/api/genqrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateQrCode(@RequestParam(required = false) String companyId,
                                                        @RequestParam(required = false) String officeId) {
        try {
            if (qrCodeConfig.isLoadFromConfig()) {
                companyId = qrCodeConfig.getCompanyId();
                officeId = qrCodeConfig.getOfficeId();
            } else {
                if (companyId.isEmpty() || officeId.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            log.info("### Started loading QR Code for company [{}], office [{}] ###", companyId, officeId);
            BaseResponse  response = qrCodeService.getQrEntity(companyId, officeId);
            BufferedImage image    = null;

            if (response.getReturnCode() == ReturnCodeEnum.PERMISSION_DENIED.getValue()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            if (response.getReturnCode() == ReturnCodeEnum.EXCEPTION.getValue()) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (response.getReturnCode() == ReturnCodeEnum.SUCCESS.getValue()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(response.getData());
                image = qrCodeService.generateQRCodeImage(objectMapper.writeValueAsString(response.getData()));
            }

            if (image == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (Exception e) {
            log.error("[generateQrCode]", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
