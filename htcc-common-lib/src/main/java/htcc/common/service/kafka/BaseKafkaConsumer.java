package htcc.common.service.kafka;

import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.StackLocatorUtil;
import org.springframework.kafka.annotation.KafkaHandler;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

@Log4j2
public abstract class BaseKafkaConsumer<T> {

    /**
     * implement riêng của mỗi consumers. if there's any exception, use try catch inside method.
     * Do not throw exception.
     *
     * @param baseMessage
     */
    public abstract void process(T baseMessage);

    /**
     * Kafka consumer cần phải gọi method này để sử lý những công việc chung như là validate message, get request id, ...
     *
     * @param message
     * @throws IOException
     */
    @KafkaHandler
    protected void handleMessage(String message) {
        Class<?> className = StackLocatorUtil.getCallerClass(2);
        try {
            if (message == null) {
                log.error(String.format("[%s] Null of converting message to object",
                        className.getSimpleName()));
                return;
            }

            Class<T> typeOfT = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];

            log.info("[{}] handleMessage: {}", typeOfT.getSimpleName(), message);
            if (typeOfT == String.class) {
                process((T) message);
                return;
            }

            T messageEnt = StringUtil.fromJsonString(message, typeOfT);
            process(messageEnt);
        } catch (Exception ex) {
            log.error(String.format("[KafkaHandleMessage] message [%s] ex", message), ex);
        }
    }
}
