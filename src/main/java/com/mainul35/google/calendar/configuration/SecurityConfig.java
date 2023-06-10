package com.mainul35.google.calendar.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2AuthorizedClientService authorizedClientService;

    public SecurityConfig(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/**", "/oauth2/callback/google").permitAll()
                        .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login -> {
                oauth2Login.authorizedClientService(authorizedClientService);
            })
            .exceptionHandling( exceptionHandling ->
                    exceptionHandling.accessDeniedPage("/unauthorized")
            );
        // @formatter:on
        return http.build();
    }


}
