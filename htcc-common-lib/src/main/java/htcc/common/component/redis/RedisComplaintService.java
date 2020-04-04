package htcc.common.component.redis;

import htcc.common.component.redis.RedisService;
import htcc.common.service.ICallback;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RedisComplaintService {

    private static final String COMPLAINT_EMPLOYEE_COUNTDOWN_LACK = "COMPLAINT-EMPLOYEE-COUNTDOWN-LACK-%s-%s-%s";

    @Autowired
    private RedisService redis;

    public void writeLock(String companyId, String username, String yyyyMM, ICallback cb) {
        String key1 = String.format(COMPLAINT_EMPLOYEE_COUNTDOWN_LACK, StringUtil.valueOf(companyId),StringUtil.valueOf(username), yyyyMM);

        RCountDownLatch latch1 = redis.getInstance().getCountDownLatch(key1);

        try {
            latch1.trySetCount(1);

            cb.callback();
        } catch (Exception e) {
            log.error("[writeLock] ex", e);
        }
    }

    public Object unlockWriteLock(String companyId, String username, String yyyyMM, ICallback cb) {
        String key1 = String.format(COMPLAINT_EMPLOYEE_COUNTDOWN_LACK, StringUtil.valueOf(companyId),StringUtil.valueOf(username), yyyyMM);

        RCountDownLatch latch1 = redis.getInstance().getCountDownLatch(key1);

        try {
            return cb.callback();
        } catch (Exception e) {
            log.error("[unlockWriteLock] ex", e);
        } finally {
            latch1.countDown();
        }

        return null;
    }

    public Object readLockForEmployee(String companyId, String username, String yyyyMM, ICallback cb) {
        return readLock(cb, COMPLAINT_EMPLOYEE_COUNTDOWN_LACK, StringUtil.valueOf(companyId), StringUtil.valueOf(username), yyyyMM);
    }

    private Object readLock(ICallback cb, String format, Object...params) {
        String key = String.format(format, params);

        RCountDownLatch latch = redis.getInstance().getCountDownLatch(key);
        try {
            latch.await();

            return cb.callback();
        } catch (Exception e) {
            log.error("[readLock] ex", e);
        }

        return null;
    }
}
