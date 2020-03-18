package htcc.gateway.service.feign;

import htcc.common.entity.base.BaseResponse;
import htcc.gateway.service.feign.fallback.EmployeeServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${zuul.routes.htcc-employee-service.serviceId}",
             fallback = EmployeeServiceClientFallback.class)
public interface EmployeeServiceClient {

    @GetMapping("/users/{companyId}/{username}")
    public BaseResponse getUserInfo(@PathVariable(required = true) String companyId,
                                    @PathVariable(required = true) String username);

}
