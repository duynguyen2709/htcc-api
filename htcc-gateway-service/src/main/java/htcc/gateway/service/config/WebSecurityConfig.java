package htcc.gateway.service.config;

import htcc.gateway.service.component.filter.JwtRequestFilter;
import htcc.gateway.service.config.file.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private SecurityConfig securityConfig;

    @Value("${eureka.dashboard.path}")
    private String eurekaDashboard;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //disable cors and csrf
        http.cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable().exceptionHandling();

        // allow public path
        http.authorizeRequests().antMatchers(allowPaths()).permitAll();

        http.authorizeRequests()
                .antMatchers(new String[]{eurekaDashboard})
                .authenticated()
                .and().formLogin();

        // jwt request for api paths
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String>      allows  = singletonList("*");
        configuration.setAllowedOrigins(allows);
        configuration.setAllowedMethods(allows);
        configuration.setAllowedHeaders(allows);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private String[] allowPaths(){
        List<String> antPatterns = new ArrayList<>();
        antPatterns.add("/");
        antPatterns.add("/login");
        antPatterns.add("/eureka/**");

        //swagger
        antPatterns.add("/swagger-ui.html");
        antPatterns.add("/v2/api-docs");
        antPatterns.add("/configuration/ui");
        antPatterns.add("/swagger-resources/**");
        antPatterns.add("/configuration/security");
        antPatterns.add("/csrf");
        antPatterns.add("/webjars/**");
        antPatterns.add("/configuration/security");

        return antPatterns.toArray(new String[0]);
    }

}
