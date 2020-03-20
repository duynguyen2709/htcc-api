package htcc.common.component;

import brave.Tracer;
import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class LoggingConfiguration {

    private static Tracer _tracer;

    @Autowired
    public void setTracer(Tracer tracer) {
        _tracer = tracer;
    }

    public static String getTraceId() {
        String traceId = _tracer.currentSpan().context().traceIdString();
        return traceId.substring(traceId.length() - 7);
    }

    @Bean
    public Sampler sleuthSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}
