package htcc.employee.service.service.redis;

import htcc.common.component.redis.RedisService;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.jpa.EmployeeInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisEmployeeInfoService {

    @Autowired
    private RedisService redis;

    @Autowired
    private EmployeeInfoRepository repo;

    public EmployeeInfo getEmployeeInfo(EmployeeInfo.Key key) {
        try {
            String raw = StringUtil.valueOf(redis.get(redis.buzConfig.employeeInfoFormat, key.getCompanyId(), key.getUsername()));
            if (raw.isEmpty()) {
                raw = setEmployeeInfo(key);
            }

            return StringUtil.fromJsonString(raw, EmployeeInfo.class);
        } catch (Exception e) {
            log.error("[getEmployeeInfo] {} ex", StringUtil.toJsonString(key), e);
            return null;
        }
    }

    private String setEmployeeInfo(EmployeeInfo.Key key) throws Exception {
        String raw = "";
        EmployeeInfo employeeInfo = repo.findById(key).orElse(null);
        if (employeeInfo == null) {
            throw new Exception("EmployeeInfoRepository.findById return null");
        }

        raw = StringUtil.toJsonString(employeeInfo);
        redis.set(raw, 86400, redis.buzConfig.employeeInfoFormat, key.getCompanyId(), key.getUsername());

        return raw;
    }

    public void deleteEmployeeInfo(EmployeeInfo.Key key) {
        redis.delete(redis.buzConfig.employeeInfoFormat, key.getCompanyId(), key.getUsername());
    }
}
