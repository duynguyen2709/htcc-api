package htcc.gateway.service.feign.fallback;

import htcc.common.entity.base.BaseResponse;
import htcc.gateway.service.feign.AdminServiceClient;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceClientFallback implements AdminServiceClient {

    @Override
    public BaseResponse getUserInfo(String username) {
        return null;
    }
}
