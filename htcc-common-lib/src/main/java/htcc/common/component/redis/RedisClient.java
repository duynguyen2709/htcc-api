package htcc.common.component.redis;

import htcc.common.entity.config.RedisBuzConfig;
import htcc.common.entity.config.RedisFileConfig;
import htcc.common.service.ICallback;
import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.codec.KryoCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log4j2
@Component
@Import({RedisFileConfig.class, RedisBuzConfig.class})
public abstract class RedisClient {

    @Autowired
    private RedisFileConfig config;

    @Autowired
    public RedisBuzConfig buzConfig;

    protected RedissonClient instance = null;

    @Bean
    @ConditionalOnProperty(name="redis.useRedis", havingValue="true")
    private RedissonClient redisClient() {
        try {
            Config redisConfig = new Config();

            if (config.isUseMaster()) {
                String[] nodeAddress = config.getNodeAddresses().split(config.getDelimiterAddress());

                nodeAddress = Arrays.stream(nodeAddress)
                        .map(address -> "redis://" + address)
                        .toArray(String[]::new);

                ReadMode readMode = ReadMode.valueOf(config.getReadMode());
                redisConfig.useMasterSlaveServers()
                        .setMasterAddress("redis://" + config.getMasterAddress())
                        .addSlaveAddress(nodeAddress)
                        .setPassword(config.getPassword())
                        .setDnsMonitoringInterval(config.getScanInterval())
                        .setSlaveConnectionMinimumIdleSize(config.getSlaveConnectionMinimumIdleSize())
                        .setSlaveConnectionPoolSize(config.getSlaveConnectionPoolSize())
                        .setMasterConnectionMinimumIdleSize(config.getMasterConnectionMinimumIdleSize())
                        .setMasterConnectionPoolSize(config.getMasterConnectionPoolSize())
                        .setIdleConnectionTimeout(config.getIdleConnectionTimeout())
                        .setConnectTimeout(config.getConnectTimeout())
                        .setTimeout(config.getResponseTimeout())
                        .setRetryAttempts(config.getRetryAttempts())
                        .setRetryInterval(config.getRetryInterval())
                        .setReadMode(readMode);
            } else {
                redisConfig.useSingleServer()
                        .setAddress("redis://" + config.getNodeAddresses())
                        .setConnectionMinimumIdleSize(config.getMasterConnectionMinimumIdleSize())
                        .setConnectionPoolSize(config.getMasterConnectionPoolSize())
                        .setIdleConnectionTimeout(config.getIdleConnectionTimeout())
                        .setConnectTimeout(config.getConnectTimeout())
                        .setTimeout(config.getResponseTimeout())
                        .setRetryAttempts(config.getRetryAttempts())
                        .setRetryInterval(config.getRetryInterval());
            }

            instance = Redisson.create(redisConfig);
            log.info("############### RedisClient Init Succeed ###############");
        } catch (Exception e){
            log.error("RedisClient Init ex: " + e.getMessage(),e);
            System.exit(3);
        }

        return instance;
    }

    public void shutdown() {
        try {
            if (instance == null) {
                return;
            }

            instance.shutdown();
            instance = null;

            log.info("Shutdown Redisson Client Successfully! ");
        } catch (Exception e) {
            log.error("RedisClient shutdown ex: " + e.getMessage(),e);
        }
    }

    protected boolean lock(String key) {
        try{
            RLock lock = instance.getLock(key);
            if (lock.isLocked())
                return true;

            lock.lock();
            return true;

        } catch (Exception e){
            log.error(String.format("Lock [%s] ex: %s", key, e.getMessage()));
            return false;
        }
    }

    protected void unlock(String key){
        try{
            RLock lock = instance.getLock(key);
            if (lock.isLocked())
                lock.unlock();

        } catch (Exception e){
            log.error(String.format("Unlock [%s] ex: %s", key, e.getMessage()));
        }
    }

    public abstract Object get(String keyFormat, Object ...params);
    public abstract void set(Object newValue, long ttl, String keyFormat, Object ...params);
    public abstract Object getOrSet(ICallback f, long ttl, String keyFormat, Object ...params);
    public abstract void delete(String keyFormat, Object ...params);

}

