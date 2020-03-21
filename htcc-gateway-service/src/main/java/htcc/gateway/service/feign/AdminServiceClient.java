package htcc.gateway.service.feign;

import htcc.common.entity.base.BaseResponse;
import htcc.gateway.service.feign.fallback.AdminServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${zuul.routes.htcc-admin-service.serviceId}",
             fallbackFactory = AdminServiceClientFallback.class)
public interface AdminServiceClient {

    @GetMapping("/users/{username}")
    public BaseResponse getUserInfo(@PathVariable(value = "username") String username);
}
