package com.mainul35.google.calendar.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Events implements Serializable {
    private String kind;
    private String etag;
    private String summary;
    private String updated;
    private String timeZone;
    private String accessRole;
    private String nextSyncToken;
    private List<Event> items;
}
