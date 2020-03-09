package htcc.gateway.service.service;

import htcc.common.service.ICallback;
import htcc.gateway.service.component.redis.RedisClient;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import javax.security.auth.callback.Callback;

@Service
@Log4j2
public class RedisService extends RedisClient {

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
    public void set(Object newValue, String keyFormat, Object... params) {
        if (instance == null) {
            return;
        }

        String key = String.format(keyFormat, params);
        RBucket<Object> bucket = instance.getBucket(key);
        bucket.set(newValue);
    }

    @Override
    public Object getOrSet(ICallback f, String keyFormat, Object... params) {
        if (instance == null) {
            return f.callback();
        }

        String key = String.format(keyFormat, params);

        RBucket<Object> bucket = instance.getBucket(key);
        if (bucket.isExists()){
            return bucket.get();
        }

        Object newValue = f.callback();

        bucket.set(newValue);
        return newValue;
    }

    @Override
    public void delete(String keyFormat, Object... params) {
        if (instance == null) {
            return;
        }

        String key = String.format(keyFormat, params);

        RBucket<Object> bucket = instance.getBucket(key);
        if (bucket.isExists()){
            bucket.delete();
        }
    }

}
