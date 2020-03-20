package htcc.employee.service.repository.feign.fallback;

import htcc.common.entity.base.BaseResponse;
import htcc.employee.service.repository.feign.LogServiceClient;
import org.springframework.stereotype.Component;

@Component
public class LogServiceClientFallback implements LogServiceClient {

    @Override
    public BaseResponse getCheckInLog(String companyId, String username, String yyyyMMdd) {
        return null;
    }

    @Override
    public BaseResponse getCheckOutLog(String companyId, String username, String yyyyMMdd) {
        return null;
    }
}
