/*
package com.mainul35.google.calendar.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.mainul35.google.calendar.configuration.GoogleServiceConfig;
import com.mainul35.google.calendar.dto.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class GoogleUserService {

    private final GoogleServiceConfig googleServiceConfig;

    public GoogleUserService(GoogleServiceConfig googleServiceConfig) {
        this.googleServiceConfig = googleServiceConfig;
    }

    public User getUserFromGoogle(String authorizationCode) throws GeneralSecurityException, IOException {
        // Get profile info from ID token
        GoogleIdToken idToken = googleServiceConfig.getTokenResponse(authorizationCode).parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();
        User user = new User();
        String userId = payload.getSubject();  // Use this value as a key to identify a user.
        String email = payload.getEmail();
        user.setEmail(email);
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        user.setName(name);
        String pictureUrl = (String) payload.get("picture");
        user.setPictureUrl(pictureUrl);
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");
        return user;
    }
}
*/
