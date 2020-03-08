//package htcc.admin.service.config;
//
//import brave.sampler.Sampler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//@Configuration
//public class LoggingConfiguration {
//
//    @Bean
//    public Sampler sleuthSampler(){
//        return Sampler.ALWAYS_SAMPLE;
//    }
//
//    @Bean
//    public CharacterEncodingFilter characterEncodingFilter() {
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8");
//        filter.setForceEncoding(true);
//        return filter;
//    }
//}
