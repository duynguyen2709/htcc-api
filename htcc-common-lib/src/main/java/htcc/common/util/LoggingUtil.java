package htcc.common.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
public class LoggingUtil {

    public static void printConfig(ConfigurableEnvironment configurableEnvironment) {
        log.info("************************* APP PROPERTIES ******************************");

        List<MapPropertySource> propertySources = new ArrayList<>();

        configurableEnvironment.getPropertySources().forEach(it -> {
            if (it instanceof MapPropertySource && it.getName().contains("applicationConfig")) {
                propertySources.add((MapPropertySource) it);
            }
        });

        propertySources.stream()
                .map(propertySource -> propertySource.getSource().keySet()).flatMap(Collection::stream).distinct().sorted().forEach(key -> {
            try {
                log.info(key + "=" + configurableEnvironment.getProperty(key));
            } catch (Exception e) {
                log.warn("{} -> {}", key, e.getMessage());
            }
        });
        log.info("************************************************************************************");

    }
}
