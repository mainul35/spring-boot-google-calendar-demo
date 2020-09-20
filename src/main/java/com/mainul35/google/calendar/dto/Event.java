package com.mainul35.google.calendar.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event implements Serializable {
    private String kind;
    private String etag;
    private String id;
    private String status;
    private String htmlLink;
    private String created;
    private String updated;
    private String summary;
    private CalendarUser creator;
    private CalendarUser organizer;
    private CalendarSchedule start;
    private CalendarSchedule end;
}
