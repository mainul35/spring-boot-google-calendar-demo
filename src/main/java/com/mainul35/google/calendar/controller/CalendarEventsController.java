package com.mainul35.google.calendar.controller;

import com.google.api.services.calendar.model.Event;
import com.mainul35.google.calendar.service.GoogleCalendarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
public class CalendarEventsController {

    private final GoogleCalendarService googleCalendarService;

    public CalendarEventsController(GoogleCalendarService googleCalendarService) {
        this.googleCalendarService = googleCalendarService;
    }

    @RequestMapping("/events")
    public String getCalendarEvents() throws IOException, GeneralSecurityException {
        List<Event> events = googleCalendarService.getCalendarEvents();
        return "events printed in console.";
    }
}
