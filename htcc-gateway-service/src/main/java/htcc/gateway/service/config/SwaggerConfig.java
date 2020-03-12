package htcc.gateway.service.config;

import com.fasterxml.classmate.TypeResolver;
import htcc.common.constant.Constant;
import htcc.common.entity.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static springfox.documentation.schema.AlternateTypeRules.newRule;
import static springfox.documentation.spring.web.paths.Paths.removeAdjacentForwardSlashes;
import static springfox.documentation.spring.web.paths.RelativePathProvider.ROOT;

@Configuration
public class SwaggerConfig {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() throws IOException {
        List<ResponseMessage> responseMessages = Collections.singletonList(new ResponseMessageBuilder()
                .code(200).message("SUCCESS").build());

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("htcc.gateway.service.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo("BaseURL : https://1612145.online/",
                        "Tất cả API chỉ thành công khi returnCode = 1",
                        "1.0",
                        null,
                        "1612145",
                        null,
                        null))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .globalOperationParameters(Arrays.asList(new ParameterBuilder()
                        .name("Authorization")
                        .description("[Header] Token khi đăng nhập")
                        .defaultValue("Bearer TOKEN")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(true)
                        .build()))
                .pathProvider(new CustomPathProvider(servletContext))
                .alternateTypeRules(newRule(
                        typeResolver.resolve(BaseResponse.class,
                        typeResolver.resolve(WildcardType.class)),
                        typeResolver.resolve(WildcardType.class))
                );
    }

    private static class CustomPathProvider extends AbstractPathProvider {
        ServletContext servletContext;

        CustomPathProvider(ServletContext servletContext) {
            this.servletContext = servletContext;
        }

        @Override
        protected String applicationPath() {
            return isNullOrEmpty(servletContext.getContextPath()) ? ROOT : servletContext.getContextPath();
        }

        @Override
        protected String getDocumentationPath() {
            return ROOT;
        }

        @Override
        public String getOperationPath(String operationPath) {
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/");
            return Constant.BASE_API_GATEWAY_PATH +
                    removeAdjacentForwardSlashes(uriComponentsBuilder.path(operationPath).build().toString());
        }
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }

}
