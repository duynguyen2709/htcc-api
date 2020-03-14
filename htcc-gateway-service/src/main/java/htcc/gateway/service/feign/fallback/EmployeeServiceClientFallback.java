package htcc.gateway.service.feign.fallback;

import htcc.common.entity.base.BaseResponse;
import htcc.gateway.service.feign.EmployeeServiceClient;
import org.springframework.stereotype.Component;

@Component
public class EmployeeServiceClientFallback implements EmployeeServiceClient {

    @Override
    public BaseResponse getUserInfo(String companyId, String username) {
        return null;
    }
}
