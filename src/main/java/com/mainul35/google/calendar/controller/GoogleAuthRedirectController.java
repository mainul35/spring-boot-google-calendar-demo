package com.mainul35.google.calendar.controller;

import com.mainul35.google.calendar.enums.SessionKey;
import com.mainul35.google.calendar.service.OauthTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.stream.Stream;

@Controller
public class GoogleAuthRedirectController {

    private final OauthTokenService oauthTokenService;

    public GoogleAuthRedirectController(OauthTokenService oauthTokenService) {
        this.oauthTokenService = oauthTokenService;
    }

    @RequestMapping("/oauth2/callback/google")
    public String callbackUrl(
            HttpServletRequest request,
            HttpSession httpSession) {
        String code = request.getParameter("code");
        String error = request.getParameter("error") == null
                ? "" : request.getParameter("error");
        String[] scopes = request.getParameter("scope").split(" ");
        if (!error.isBlank() || code.isBlank()) return "errors/unauthorized";
        String scopeWithCalendarPermission =
                Stream.of(scopes).filter(s -> s.contains("calendar")).findFirst().get();
        httpSession
                .setAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString(),
                        oauthTokenService.fetchToken(code, scopeWithCalendarPermission)
                );
        return "redirect:/agenda";
    }
}
