package htcc.web.service.service;

import htcc.web.service.config.ResetPasswordConfig;
import htcc.web.service.entity.BaseResponse;
import htcc.web.service.entity.ResetPasswordRequest;
import htcc.web.service.utils.HashUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class ResetPasswordService {

    @Autowired
    private ResetPasswordConfig resetPasswordConfig;

    public BaseResponse resetPassword(ResetPasswordRequest request) throws Exception {
        log.info(request);
        String sig = HashUtil.hashSHA256(String.format("%s|%s|%s|%s",
                request.getClientId(), request.getCompanyId(), request.getUsername(), resetPasswordConfig.getHashKey()));

        String url = String.format("%s%s?sig=%s", resetPasswordConfig.getBaseUrl(), resetPasswordConfig.getMethodName(), sig);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        HttpEntity<ResetPasswordRequest> entity = new HttpEntity<>(request);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate.postForObject(url, entity, BaseResponse.class);
    }
}
