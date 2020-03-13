package htcc.gateway.service.feign;

import htcc.common.entity.base.BaseResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${zuul.routes.htcc-admin-service.serviceId}",
             fallback = AdminServiceClientFallback.class)
public interface AdminServiceClient {

    @GetMapping("/users/{username}")
    public BaseResponse getUserInfo(@PathVariable(value = "username") String username);
}

@Component
class AdminServiceClientFallback implements AdminServiceClient {

    @Override
    public BaseResponse getUserInfo(String username) {
        return null;
    }
}
