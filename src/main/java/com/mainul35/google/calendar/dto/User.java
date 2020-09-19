package com.mainul35.google.calendar.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String email;
    private String name;
    private String pictureUrl;
}
