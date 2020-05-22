package htcc.common.component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.TransactionalMap;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionOptions;
import htcc.common.constant.CacheKeyEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class HazelcastService {

    @Value("${service.hazelcast.useHazelcast:false}")
    private boolean useHazelcast;

    @Autowired(required = false)
    private HazelcastInstance hazelcastInstance;

    private <K, V> IMap<K, V> getMap(String key) {
        return hazelcastInstance.getMap(key);
    }

    private TransactionContext getTransactionContext() {
        TransactionOptions transOp = (new TransactionOptions())
                .setTimeout(10L, TimeUnit.SECONDS)
                .setTransactionType(TransactionOptions.TransactionType.ONE_PHASE);

        return hazelcastInstance.newTransactionContext(transOp);
    }

    private <K, V> void reloadMap(TransactionContext context, String key, Map<K, V> valMap) {
        TransactionalMap<K, V> transMap = context.getMap(key);
        transMap.keySet().forEach(transMap::remove);
        valMap.forEach(transMap::put);
    }

    public  <K, V> Map<K, V> reload(Map<K, V> mapFromDb, CacheKeyEnum hazelCastKey){

        if (!useHazelcast) {
            return mapFromDb;
        }

        TransactionContext context = getTransactionContext();

        try {
            context.beginTransaction();
            reloadMap(context, hazelCastKey.name(), mapFromDb);
            context.commitTransaction();
        } catch (Exception ex) {
            context.rollbackTransaction();
            log.error(String.format("[reload] load [%s] ex: %s", hazelCastKey.name(), ex.getMessage()), ex);
            return mapFromDb;
        }

        return getMap(hazelCastKey.name());
    }
}
