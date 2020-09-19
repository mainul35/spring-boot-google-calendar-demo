package com.mainul35.google.calendar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
public class GoogleAuthRedirectController {

    @RequestMapping("/Callback")
    public String callbackUrl(
            @RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "error", defaultValue = "") String error) {
        if (!error.isBlank()) return "errors/unauthorized";
//        User user = googleUserService.getUserFromGoogle(code);
        return "redirect:/events";
    }
}
