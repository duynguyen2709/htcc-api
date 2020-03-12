package htcc.common.util;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SwaggerUtil {

    public static Docket init(String basePackage, String baseUrl){
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
}
