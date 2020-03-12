package htcc.common.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@Log4j2
public class SwaggerDocsConfig {

    @Autowired
    private Environment env;

    @Bean
    public Docket docket(){
        String basePackage = env.getProperty("basePackage");
        String baseUrl = env.getProperty("baseUrl");

        List<ResponseMessage> responseMessages = Collections.singletonList(new ResponseMessageBuilder()
                .code(200).message("SUCCESS").build());

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo("BaseURL : " + baseUrl,
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
                        .build()));
    }

    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
    }
}
