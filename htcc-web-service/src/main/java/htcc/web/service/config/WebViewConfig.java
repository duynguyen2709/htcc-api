//package htcc.web.service.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.BufferedImageHttpMessageConverter;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.servlet.config.annotation.*;
//
//import htcc.web.service.properties.AppProperties;
//
//import java.awt.image.BufferedImage;
//
//@Configuration
//@EnableWebMvc
//public class WebViewConfig implements WebMvcConfigurer {
////
////	@Autowired
////	private AppProperties appProperties;
////
////	@Override
////	public void addResourceHandlers(ResourceHandlerRegistry registry) {
////		registry.addResourceHandler(appProperties.entranceDomain + "/**", "/**")
////				.addResourceLocations("classpath:/static/")
////				.setCachePeriod(31556926);
////	}
////
////	@Bean
////	public RestTemplate restTemplate(RestTemplateBuilder builder) {
////		return builder.build();
////	}
//
//	@Bean
//	public WebMvcConfigurer corsConfiguration() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:8080")
//						.allowedMethods("GET");
//			}
//		};
//	}
//}
