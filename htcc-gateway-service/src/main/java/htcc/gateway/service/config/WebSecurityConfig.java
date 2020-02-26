package htcc.gateway.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

@EnableWebSecurity
@Component
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.user.name}")
    private String username;

    @Value("${security.user.password}")
    private String password;

    @Value("${eureka.dashboard.path}")
    private String eurekaDashboard;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(username)
                .password(password)
                .authorities("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());

        http.csrf().disable().authorizeRequests()
                .antMatchers(allowPaths().toArray(new String[0])).permitAll()
                .and().exceptionHandling();

        super.configure(http);

    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String>      allowOrigins  = singletonList("*");
        configuration.setAllowedOrigins(allowOrigins);
        configuration.setAllowedMethods(singletonList("*"));
        configuration.setAllowedHeaders(singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private List<String> allowPaths(){
        List<String> antPatterns = new ArrayList<>();
        antPatterns.add("/");
        antPatterns.add("/login");
        antPatterns.add("/api/**");
        antPatterns.add("/public/**");

        //swagger
        antPatterns.add("/swagger-ui.html");
        antPatterns.add("/v2/api-docs");
        antPatterns.add("/configuration/ui");
        antPatterns.add("/swagger-resources/**");
        antPatterns.add("/configuration/security");
        antPatterns.add("/csrf");
        antPatterns.add("/webjars/**");
        antPatterns.add("/configuration/security");

        return antPatterns;

    }
}
