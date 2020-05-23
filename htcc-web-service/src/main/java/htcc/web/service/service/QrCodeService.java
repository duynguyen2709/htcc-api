package htcc.web.service.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import htcc.web.service.config.QrCodeConfig;
import htcc.web.service.entity.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.image.BufferedImage;

@Service
public class QrCodeService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QrCodeConfig qrCodeConfig;

    public BaseResponse getQrEntity(String companyId, String officeId) {
        String url = String.format("%s%s?companyId=%s&officeId=%s", qrCodeConfig.getBaseUrl(),
                qrCodeConfig.getMethodName(), companyId, officeId);

        if (qrCodeConfig.isGenSig()) {
            url += "&sig=" + genSig(companyId, officeId);
        }
        return restTemplate.getForObject(url, BaseResponse.class);
    }

    private String genSig(String companyId, String officeId) {
        return "";
    }

    public BufferedImage generateQRCodeImage(String data) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(data, BarcodeFormat.QR_CODE, 256, 256);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
