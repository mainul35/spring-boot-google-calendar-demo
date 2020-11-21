package com.mainul35.google.calendar.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class File implements Serializable {
    private String kind;
    private String id;
    private String name;
    private String mimeType;

}
