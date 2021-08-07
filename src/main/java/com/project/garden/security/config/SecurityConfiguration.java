package com.project.garden.security.config;

import com.project.garden.security.user.model.Role;
import com.project.garden.security.user.service.UserService;
import com.project.garden.core.context.ContextHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration(value = "SecurityConfiguration")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private final Environment environment;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ContextHolderService contextHolderService;
    @Autowired
    @Qualifier("securityUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SecurityConfiguration(Environment environment, UserService userService, ContextHolderService contextHolderService) {
        this.environment = environment;
        this.userService = userService;
        this.contextHolderService = contextHolderService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/security/**")
                .permitAll()
                .antMatchers("/admin/**")
                .hasAuthority(Role.ADMIN.name())
                .antMatchers("/customer/**")
                .hasAuthority(Role.CUSTOMER.name())
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), environment, userService, contextHolderService))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
//        config.setAllowCredentials(true);
//        String web = environment.getProperty("web.url");
//        String panel = environment.getProperty("panel.url");
//        String header = environment.getProperty("dashboard.security.token.request.header");
//        config.addAllowedOrigin("*");
//        config.setAllowedOrigins(Arrays.asList("*", panel, web));
//        config.setAllowedHeaders(Arrays.asList("*", header));
//        config.setAllowedMethods(Arrays.asList("*", "OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
}
