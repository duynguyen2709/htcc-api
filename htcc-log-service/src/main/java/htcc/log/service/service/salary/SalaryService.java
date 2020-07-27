package htcc.log.service.service.salary;

import htcc.common.component.redis.RedisService;
import htcc.common.entity.payslip.SalaryLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    @Autowired
    private RedisService redis;

    public String genPayslipId(SalaryLogEntity logEntity) {
        String date = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyMMdd");
        String paySlipId = StringUtil.valueOf(redis.get(
                redis.buzConfig.getPaySlipIdFormat(), logEntity.getCompanyId(), date));
        if (paySlipId.isEmpty()) {
            paySlipId = String.format("%s-%s-0001", logEntity.getCompanyId(), date);
        }

        long nextId = Long.parseLong(paySlipId.substring(paySlipId.length() - 4)) + 1;
        String nextIdStr = StringUtil.valueOf(nextId);
        if (nextId < 10) {
            nextIdStr = "000" + nextIdStr;
        }
        else if (nextId < 100) {
            nextIdStr = "00" + nextIdStr;
        }
        else if (nextId < 1000) {
            nextIdStr = "0" + nextIdStr;
        }

        String nextPayslipId = String.format("%s-%s-%s", logEntity.getCompanyId(), date, nextIdStr);
        redis.set(nextPayslipId, DateTimeUtil.getSecondUntilEndOfDay(),
                redis.buzConfig.getPaySlipIdFormat(), logEntity.getCompanyId(), date);

        return paySlipId;
    }
}
