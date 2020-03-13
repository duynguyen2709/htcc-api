//package htcc.gateway.service.component.redis;
//
//import htcc.common.service.ICallback;
//import htcc.gateway.service.config.file.RedisFileConfig;
//import lombok.extern.log4j.Log4j2;
//import org.redisson.Redisson;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.redisson.codec.KryoCodec;
//import org.redisson.config.Config;
//import org.redisson.config.ReadMode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//@Log4j2
//@Component
//public abstract class RedisClient {
//
//    @Autowired
//    private RedisFileConfig config;
//
//    protected RedissonClient instance = null;
//
//    public boolean useRedis = false;
//
//    @Bean
//    private RedissonClient redisClient() {
//        useRedis = config.isUseRedis();
//        if (!useRedis) {
//            return instance;
//        }
//
//        try {
//            Config redisConfig = new Config();
//
//            if (config.isKryoCodec()) {
//                redisConfig.setCodec(new KryoCodec());
//            }
//
//            if (config.isUseMaster()) {
//                String[] nodeAddress = config.getNodeAddresses().split(config.getDelimiterAddress());
//
//                nodeAddress = Arrays.stream(nodeAddress)
//                        .map(address -> "redis://" + address)
//                        .toArray(String[]::new);
//
//                ReadMode readMode = ReadMode.valueOf(config.getReadMode());
//                redisConfig.useMasterSlaveServers()
//                        .setMasterAddress("redis://" + config.getMasterAddress())
//                        .addSlaveAddress(nodeAddress)
//                        .setPassword(config.getPassword())
//                        .setDnsMonitoringInterval(config.getScanInterval())
//                        .setSlaveConnectionMinimumIdleSize(config.getSlaveConnectionMinimumIdleSize())
//                        .setSlaveConnectionPoolSize(config.getSlaveConnectionPoolSize())
//                        .setMasterConnectionMinimumIdleSize(config.getMasterConnectionMinimumIdleSize())
//                        .setMasterConnectionPoolSize(config.getMasterConnectionPoolSize())
//                        .setIdleConnectionTimeout(config.getIdleConnectionTimeout())
//                        .setConnectTimeout(config.getConnectTimeout())
//                        .setTimeout(config.getResponseTimeout())
//                        .setRetryAttempts(config.getRetryAttempts())
//                        .setRetryInterval(config.getRetryInterval())
//                        .setReadMode(readMode);
//            } else {
//                redisConfig.useSingleServer()
//                        .setAddress("redis://" + config.getNodeAddresses())
//                        .setConnectionMinimumIdleSize(config.getMasterConnectionMinimumIdleSize())
//                        .setConnectionPoolSize(config.getMasterConnectionPoolSize())
//                        .setIdleConnectionTimeout(config.getIdleConnectionTimeout())
//                        .setConnectTimeout(config.getConnectTimeout())
//                        .setTimeout(config.getResponseTimeout())
//                        .setRetryAttempts(config.getRetryAttempts())
//                        .setRetryInterval(config.getRetryInterval());
//            }
//
//            instance = Redisson.create(redisConfig);
//        } catch (Exception e){
//            log.error("RedisClient Init ex: " + e.getMessage(),e);
//            System.exit(3);
//        }
//
//        return instance;
//    }
//
//    public void shutdown() {
//        try {
//            if (instance == null) {
//                return;
//            }
//
//            instance.shutdown();
//            instance = null;
//
//            log.info("Shutdown Redisson Client Successfully! ");
//        } catch (Exception e) {
//            log.error("RedisClient shutdown ex: " + e.getMessage(),e);
//        }
//    }
//
//    protected boolean lock(String key) {
//        try{
//            RLock lock = instance.getLock(key);
//            if (lock.isLocked())
//                return true;
//
//            lock.lock();
//            return true;
//
//        } catch (Exception e){
//            log.info(String.format("Lock [%s] ex: %s", key, e.getMessage()));
//            return false;
//        }
//    }
//
//    protected void unlock(String key){
//        try{
//            RLock lock = instance.getLock(key);
//            if (lock.isLocked())
//                lock.unlock();
//
//        } catch (Exception e){
//            log.info(String.format("Unlock [%s] ex: %s", key, e.getMessage()));
//        }
//    }
//
//    public abstract Object get(String keyFormat, Object ...params);
//    public abstract void set(Object newValue, long ttl, String keyFormat, Object ...params);
//    public abstract Object getOrSet(ICallback f, long ttl, String keyFormat, Object ...params);
//    public abstract void delete(String keyFormat, Object ...params);
//
//}
