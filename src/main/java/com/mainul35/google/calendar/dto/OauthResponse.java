package com.mainul35.google.calendar.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OauthResponse implements Serializable {
    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
