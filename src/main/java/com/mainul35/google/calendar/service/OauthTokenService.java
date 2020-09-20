package com.mainul35.google.calendar.service;

import com.mainul35.google.calendar.dto.OauthResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OauthTokenService {

    private final RestTemplate restTemplate;

    public OauthTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchToken(String code, String scope) {
        final String uri = "https://accounts.google.com/o/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic MzE2NTA4Mzg3MzUwLTlmY2txczllcDV2Y3QzaWFyNDIyYjA5NnVkNXJpcjZwLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tOnBZV2RhQ2ZVTGlhNnpfczB2eW5nSmV3dA==");
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", "http://localhost:8080/oauth2/callback/google");
        requestBody.add("scope", scope);

        HttpEntity formEntity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);

        ResponseEntity<OauthResponse> response = restTemplate.exchange(uri, HttpMethod.POST, formEntity,  OauthResponse.class);
        return response.getBody().getAccess_token();
    }
}
