package org.kursach.config;

import org.kursach.repository.JwtTokenRepository;
import org.kursach.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@ComponentScan("org.kursach")
public class SecurityConfig {

    private final UserService service;
    private final JwtTokenRepository jwtTokenRepository;
    private final HandlerExceptionResolver resolver;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(UserService service, JwtTokenRepository jwtTokenRepository,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver, AuthenticationProvider authenticationProvider) {
        this.service = service;
        this.jwtTokenRepository = jwtTokenRepository;
        this.resolver = resolver;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement( (management) -> management
                                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                        )
                .addFilterBefore(new JwtCsrfFilter(jwtTokenRepository, resolver), CsrfFilter.class)
                .authenticationProvider(authenticationProvider)
                .csrf( (csrf) -> csrf
                        .ignoringRequestMatchers("/**")
                )
                .authorizeRequests( (authReq) -> authReq
                        .requestMatchers("/auth/login")
                                .permitAll()
                        .requestMatchers("/auth/registration")
                                .permitAll()
                        .requestMatchers("/auth/logout")
                                .permitAll()
                )
                .httpBasic( (httpBasic) -> httpBasic
                        .authenticationEntryPoint((request, response, e) -> resolver.resolveException(request, response, null, e))
                );

        return http.build();
    }
}