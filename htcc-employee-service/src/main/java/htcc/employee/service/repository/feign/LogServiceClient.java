package htcc.employee.service.repository.feign;

import htcc.common.entity.base.BaseResponse;
import htcc.employee.service.repository.feign.fallback.LogServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "htcc-log-service",
             fallback = LogServiceClientFallback.class)
public interface LogServiceClient {

    @GetMapping("/internal/logs/checkin")
    public BaseResponse getCheckInLog(@RequestParam(name = "companyId") String companyId,
                                      @RequestParam(name = "username") String username,
                                      @RequestParam(name = "yyyyMMdd") String yyyyMMdd);

    @GetMapping("/internal/logs/checkout")
    public BaseResponse getCheckOutLog(@RequestParam(name = "companyId") String companyId,
                                      @RequestParam(name = "username") String username,
                                      @RequestParam(name = "yyyyMMdd") String yyyyMMdd);
}
