package com.api.config;

import com.api.enums.RoleEnum;
import com.api.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author Miguel Castro
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTConfig jwtConfig;

    @Autowired
    private JWTService jwtService;

    private static final String[] SWAGGER_PATHS = {
        "/api/v1/auth/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/v3/api-docs/**",
        "/v3/api-docs.yaml",
        "/panksys_finance_doc"
    };

    private static final String[] USERS_PATHS = {
        "/api/v1/user/message",
        "/api/v1/user/update/{id}",
        "/api/v1/user/disabled/{id}",
        "/api/v1/simple-finance/**"
    };

    private static final String[] ADMINS_PATHS = {
        "/api/v1/admin/message"
    };

    private static final String[] SIMPLE_FINANCE_PATHS = {
        "/api/v1/simple-finance/save",
        "/api/v1/simple-finance/update/{id}",
        "/api/v1/simple-finance/list",
        "/api/v1/simple-finance/find/{id}",
        "/api/v1/simple-finance/delete/{id}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                .requestMatchers(SWAGGER_PATHS).permitAll()
                .requestMatchers(ADMINS_PATHS).hasAnyAuthority(RoleEnum.ADMIN.name())
                .requestMatchers(USERS_PATHS).hasAnyAuthority(RoleEnum.USER.name())
                .requestMatchers(SIMPLE_FINANCE_PATHS).hasAnyAuthority(RoleEnum.USER.name())
                .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                jwtConfig, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(jwtService.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
