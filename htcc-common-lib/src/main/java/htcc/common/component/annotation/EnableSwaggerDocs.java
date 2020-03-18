package htcc.common.component.annotation;

import htcc.common.component.SwaggerAnnotationConfig;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Service
@Import({SwaggerAnnotationConfig.class})
public @interface EnableSwaggerDocs {

    String basePackage();

    String baseUrl();
}
