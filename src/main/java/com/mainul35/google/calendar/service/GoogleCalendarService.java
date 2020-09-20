package com.mainul35.google.calendar.service;

import com.google.gson.Gson;
import com.mainul35.google.calendar.dto.Event;
import com.mainul35.google.calendar.dto.Events;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
        return events.getItems().stream().filter(this::checkIfTheEventIsWithinRange)
        .map(event -> {
          String start = parseDateTime(event.getStart().getDateTime());
          String end = parseDateTime(event.getEnd().getDateTime());
          event.getStart().setDateTime(start);
          event.getEnd().setDateTime(end);
          return event;
        })
                .collect(Collectors.toList());
    }

    public String parseDateTime(String datetime) {
        String time = datetime.replace("+", " ").split(" ")[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalDateTime localDateTime = LocalDateTime.parse(time);
        return localDateTime.format(formatter);
    }

    public boolean checkIfTheEventIsWithinRange(Event event) {
        String time = event.getStart().getDateTime().replace("+", " ").split(" ")[0];
        LocalDateTime localDateTime = LocalDateTime.parse(time);
        LocalDateTime now = LocalDateTime.now();
        int endHour = (LocalDateTime.now().getHour() - (LocalDateTime.now().getHour() % 24)) + 24;
        return ((int)Duration.between(now, localDateTime).getSeconds())/(60*60*24) >= 0
                && localDateTime.getHour() < endHour;
    }
}
