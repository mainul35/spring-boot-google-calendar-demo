package com.mainul35.google.calendar.service;

import com.google.gson.Gson;
import com.mainul35.google.calendar.dto.Event;
import com.mainul35.google.calendar.dto.Events;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleCalendarService {

    private final RestTemplate restTemplate;

    public GoogleCalendarService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Event> getCalendarEvents(String accessToken) {
        String requestUri = "https://www.googleapis.com/calendar/v3/calendars/primary/events";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity request = new HttpEntity(headers);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, request, String.class);

        Gson gson = new Gson();
        Events events = gson.fromJson(response.getBody(), Events.class);
        List<Event> eventList = events
                .getItems()
                .stream()
                // Filter only today's events
                .filter(this::checkIfTheEventIsWithinRange)
                .map(this::setOnlyTimeToEvent)
                .collect(Collectors.toList());

        // Sort by ending time
        sortByEndTime(eventList);
        return eventList;
    }

    private List<Event> sortByEndTime(List<Event> events) {
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                LocalTime startO1 = LocalTime.parse(o1.getStart().getDateTime(), formatter);
                LocalTime endO1 = LocalTime.parse(o1.getEnd().getDateTime(), formatter);
                LocalTime startO2 = LocalTime.parse(o2.getStart().getDateTime(), formatter);
                LocalTime endO2 = LocalTime.parse(o2.getEnd().getDateTime(), formatter);
                if (startO1.getHour() != startO2.getHour()) return 0;
                return startO1.minusHours(startO2.getHour()).getHour() == 0
                        && startO1.minusMinutes(startO2.getMinute()).getMinute() == 0
                        && endO2.getHour() == endO1.getHour()
                        && endO2.getMinute() <= endO1.getMinute() ? 1 : -1;
            }
        });
        return events;
    }

    private Event setOnlyTimeToEvent(Event event) {
        String start = parseDateTime(event.getStart().getDateTime());
        String end = parseDateTime(event.getEnd().getDateTime());
        event.getStart().setDateTime(start);
        event.getEnd().setDateTime(end);
        return event;
    }
    private String parseDateTime(String datetime) {
        String time = datetime.replace("+", " ").split(" ")[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalDateTime localDateTime = LocalDateTime.parse(time);
        return localDateTime.format(formatter);
    }

    private boolean checkIfTheEventIsWithinRange(Event event) {
        String time = event.getStart().getDateTime().replace("+", " ").split(" ")[0];
        LocalDateTime localDateTime = LocalDateTime.parse(time);
        LocalDateTime now = LocalDateTime.now();
        int endHour = 24 - now.getHour();
        return localDateTime.isAfter(now)
                && localDateTime.isBefore(now.plusHours(endHour));
    }
}
