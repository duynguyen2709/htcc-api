package htcc.web.service.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import htcc.web.service.config.QrCodeConfig;
import htcc.web.service.entity.BaseResponse;
import htcc.web.service.utils.HashUtil;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.image.BufferedImage;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

@Service
public class QrCodeService {

    @Autowired
    private QrCodeConfig qrCodeConfig;

    public BaseResponse getQrEntity(String companyId, String officeId) throws Exception {
        final String sig = HashUtil.hashSHA256(String.format("%s|%s|%s",
                companyId, officeId, qrCodeConfig.getHashKey()));

        final String url = String.format("%s%s?companyId=%s&officeId=%s&sig=%s",
                qrCodeConfig.getBaseUrl(), qrCodeConfig.getMethodName(), companyId, officeId, sig);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate.getForObject(url, BaseResponse.class);
    }

    public BufferedImage generateQRCodeImage(String data) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(data, BarcodeFormat.QR_CODE, 256, 256);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
