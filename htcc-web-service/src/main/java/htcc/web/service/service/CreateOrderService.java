package htcc.web.service.service;

import htcc.web.service.config.CreateOrderConfig;
import htcc.web.service.entity.BaseResponse;
import htcc.web.service.entity.CreateOrderRequest;
import htcc.web.service.entity.ResetPasswordRequest;
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
public class CreateOrderService {

    @Autowired
    private CreateOrderConfig createOrderConfig;

    public BaseResponse createOrder(CreateOrderRequest request) throws Exception {
        String sig = HashUtil.hashSHA256(String.format("%s|%s|%s",
                request.getCompanyId(), request.getEmail(), createOrderConfig.getHashKey()));

        String url = String.format("%s%s?sig=%s", createOrderConfig.getBaseUrl(), createOrderConfig.getMethodName(), sig);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        HttpEntity<CreateOrderRequest> entity = new HttpEntity<>(request);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate.postForObject(url, entity, BaseResponse.class);
    }
}
