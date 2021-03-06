package htcc.common.component.redis;

import htcc.common.service.ICallback;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class RedisService extends RedisClient {

    private static final String LOCK = "LOCK-";

    @Override
    public Object get(String keyFormat, Object... params) {
        if (instance == null) {
            return null;
        }

        String key = String.format(keyFormat, params);

        RBucket<Object> bucket = instance.getBucket(key);
        if (bucket.isExists()){
            return bucket.get();
        }

        return null;
    }

    @Override
    public void set(Object newValue, long ttl, String keyFormat, Object... params) {
        if (instance == null) {
            return;
        }

        String key = String.format(keyFormat, params);
        try {
            if (!lock(LOCK + key)) {
                throw new Exception("Lock " + key + " failed!");
            }

            RBucket<Object> bucket = instance.getBucket(key);

            if (ttl <= 0) {
                bucket.set(newValue);
            }
            else {
                bucket.set(newValue, ttl, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error(String.format("Redis Set [%s] ex %s : ", key, e.getMessage()),e);
        } finally {
            unlock(LOCK + key);
        }
    }

    @Override
    public Object getOrSet(ICallback f, long ttl, String keyFormat, Object... params) {
        if (instance == null) {
            return f.callback();
        }

        String key = String.format(keyFormat, params);
        try {
            if (!lock(LOCK + key)) {
                throw new Exception("Lock " + key + " failed!");
            }

            RBucket<Object> bucket = instance.getBucket(key);
            if (bucket.isExists()) {
                return bucket.get();
            }

            Object newValue = f.callback();

            if (ttl <= 0) {
                bucket.set(newValue);
            } else {
                bucket.set(newValue, ttl, TimeUnit.SECONDS);
            }

            return newValue;
        } catch (Exception e){
            log.error(String.format("Redis getOrSet [%s] ex %s : ", key, e.getMessage()),e);
            return null;
        } finally {
            unlock(LOCK + key);
        }
    }

    @Override
    public void delete(String keyFormat, Object... params) {
        if (instance == null) {
            return;
        }

        String key = String.format(keyFormat, params);

        try {
            if (!lock(LOCK + key)) {
                throw new Exception("Lock " + key + " failed!");
            }

            RBucket<Object> bucket = instance.getBucket(key);
            if (bucket.isExists()) {
                bucket.delete();
            }
        } catch (Exception e) {
            log.error(String.format("Redis delete [%s] ex %s : ", key, e.getMessage()),e);
        } finally {
            unlock(LOCK + key);
        }
    }

}
