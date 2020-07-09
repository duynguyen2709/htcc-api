package htcc.web.service.service;

import htcc.web.service.config.SubmitTransConfig;
import htcc.web.service.entity.BaseResponse;
import htcc.web.service.entity.SubmitTransRequest;
import htcc.web.service.utils.HashUtil;
import htcc.web.service.utils.StringUtil;
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
public class SubmitTransService {

    @Autowired
    private SubmitTransConfig config;

    public BaseResponse submitTrans(SubmitTransRequest request) throws Exception {
        String sig = HashUtil.hashSHA256(String.format("%s|%s", request.getOrderId(), config.getHashKey()));

        String url = String.format("%s%s?sig=%s", config.getBaseUrl(), config.getMethodName(), sig);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        HttpEntity<SubmitTransRequest> entity = new HttpEntity<>(request);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate.postForObject(url, entity, BaseResponse.class);
    }
}
