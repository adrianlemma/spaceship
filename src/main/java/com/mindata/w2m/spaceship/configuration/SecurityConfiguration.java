package com.mindata.w2m.spaceship.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.PrintWriter;

import static com.mindata.w2m.spaceship.constant.ErrorEnum.FORBIDDEN_ERROR;
import static com.mindata.w2m.spaceship.constant.ErrorEnum.UNAUTHORIZED_ERROR;

@Configuration
public class SecurityConfiguration {

    @Value("${spaceship.security.user.name}")
    private String userName;

    @Value("${spaceship.security.user.pass}")
    private String userPass;

    @Value("${spaceship.security.user.role}")
    private String userRole;

    @Value("${spaceship.security.admin.name}")
    private String adminName;

    @Value("${spaceship.security.admin.pass}")
    private String adminPass;

    @Value("${spaceship.security.admin.role}")
    private String adminRole;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").hasRole(userRole)
                        .requestMatchers(HttpMethod.PUT, "/**").hasRole(userRole)
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole(adminRole)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorized())
                        .accessDeniedHandler(accessDenied()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();
        UserDetails user = User.withUsername(userName).password(encoder.encode(userPass)).roles(userRole).build();
        UserDetails admin = User.withUsername(adminName).password(encoder.encode(adminPass)).roles(adminRole, userRole).build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationEntryPoint unauthorized() {
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter writer = response.getWriter();
            writer.write("{\"error_code\":\"" + UNAUTHORIZED_ERROR.getCode() + "\",\"error_message\":\"" + UNAUTHORIZED_ERROR.getDescription() + "\"}");
            writer.flush();
        };
    }

    private AccessDeniedHandler accessDenied() {
        return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            PrintWriter writer = response.getWriter();
            writer.write("{\"error_code\":\"" + FORBIDDEN_ERROR.getCode() + "\",\"error_message\":\"" + FORBIDDEN_ERROR.getDescription() + "\"}");
            writer.flush();
        };
    }

}
