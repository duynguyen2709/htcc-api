package htcc.gateway.service.config;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.FileReader;
import java.io.IOException;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() throws IOException, XmlPullParserException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();

        Model model = reader.read(new FileReader("pom.xml"));

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("htcc.gateway.service.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo("HTCC Api Documentation",
                        "Documentation automatically generated from Swagger",
                        model.getParent().getVersion(),
                        null,
                        "1612145",
                        null,
                        null));
    }

}
