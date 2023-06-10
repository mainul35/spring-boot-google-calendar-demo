package com.mainul35.google.calendar.controller;

import com.mainul35.google.calendar.dto.Event;
import com.mainul35.google.calendar.enums.SessionKey;
import com.mainul35.google.calendar.exception.CalendarAccessDeniedException;
import com.mainul35.google.calendar.service.GoogleCalendarService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CalendarEventsController {

    private final GoogleCalendarService googleCalendarService;

    public CalendarEventsController(GoogleCalendarService googleCalendarService, ClientRegistrationRepository clientRegistrationRepository, RestTemplate restTemplate) {
        this.googleCalendarService = googleCalendarService;
    }

    @RequestMapping("/agenda")
    public String getCalendarEvents(HttpSession session, Model model) {
        String accessToken =  session.getAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString()) == null
                ? "" : session.getAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString()).toString();

        if (accessToken == null || accessToken.isBlank()) {
            throw new CalendarAccessDeniedException("Invalid token");
        }
        List<Event> events = googleCalendarService.getCalendarEvents(accessToken);
        model.addAttribute("events", events);
        return "agenda";
    }
}
