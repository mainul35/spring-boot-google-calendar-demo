package com.mainul35.google.calendar.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final OAuth2AccessTokenResponseClient accessTokenResponseClient;
    private final AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientService authorizedClientService, OAuth2AccessTokenResponseClient accessTokenResponseClient, AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientService = authorizedClientService;
        this.accessTokenResponseClient = accessTokenResponseClient;
        this.authorizationRequestRepository = authorizationRequestRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/**", "/oauth2/callback/google").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository)
                .authorizedClientService(authorizedClientService);
        // @formatter:on
    }


}
