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
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class QrCodeService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QrCodeConfig qrCodeConfig;

    public BaseResponse getQrEntity(String companyId, String officeId) throws Exception {
        final String sig = hashSHA256(String.format("%s|%s|%s",
                companyId, officeId, qrCodeConfig.getHashKey()));

        final String url = String.format("%s%s?companyId=%s&officeId=%s&sig=%s",
                qrCodeConfig.getBaseUrl(), qrCodeConfig.getMethodName(), companyId, officeId, sig);

        return restTemplate.getForObject(url, BaseResponse.class);
    }

    public BufferedImage generateQRCodeImage(String data) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(data, BarcodeFormat.QR_CODE, 256, 256);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private String hashSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] shaByteArr = mDigest.digest(input.getBytes(Charset.forName("UTF-8")));
        StringBuilder hexString = new StringBuilder();

        for (byte b : shaByteArr) {
            String hex = Integer.toHexString(255 & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
