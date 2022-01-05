package com.teamfresh.voc.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class LoginResponseDto {
    private String username;
    private String message;
    private int code;
    private String token;

    @Builder
    public LoginResponseDto(String username, String message, int code,String token) {
        this.code = code;
        this.message = message;
        this.username = username;
        this.token = token;
    }
}
