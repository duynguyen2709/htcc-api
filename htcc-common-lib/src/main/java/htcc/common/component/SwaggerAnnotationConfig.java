package htcc.common.component;

import htcc.common.component.annotation.EnableSwaggerDocs;
import htcc.common.config.SwaggerDocsConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class SwaggerAnnotationConfig implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes
                                (EnableSwaggerDocs.class.getName(), false));
        System.setProperty("basePackage", attributes.getString("basePackage"));
        System.setProperty("baseUrl", attributes.getString("baseUrl"));
        return new String[]{SwaggerDocsConfig.class.getName()};

    }
}

