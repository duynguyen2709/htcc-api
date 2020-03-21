package htcc.gateway.service.feign.fallback;

import feign.hystrix.FallbackFactory;
import htcc.common.entity.base.BaseResponse;
import htcc.gateway.service.feign.EmployeeServiceClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class EmployeeServiceClientFallback implements FallbackFactory<EmployeeServiceClient> {

    @Override
    public EmployeeServiceClient create(Throwable throwable) {

        return new EmployeeServiceClient() {
            @Override
            public BaseResponse getUserInfo(String companyId, String username) {
                log.warn("EmployeeServiceClientFallback caused by " + throwable.getMessage());
                return null;
            }
        };
    }
}
