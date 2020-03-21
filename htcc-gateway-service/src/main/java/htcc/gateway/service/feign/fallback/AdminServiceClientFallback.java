package htcc.gateway.service.feign.fallback;

import feign.hystrix.FallbackFactory;
import htcc.common.entity.base.BaseResponse;
import htcc.gateway.service.feign.AdminServiceClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AdminServiceClientFallback implements FallbackFactory<AdminServiceClient> {

    @Override
    public AdminServiceClient create(Throwable throwable) {

        return new AdminServiceClient() {
            @Override
            public BaseResponse getUserInfo(String username) {
                log.warn("AdminServiceClientFallback caused by " + throwable.getMessage());
                return null;
            }
        };
    }
}
