package com.teamfresh.voc.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpResponseDto {

    private String username;
    private String message;
    private int code;

    @Builder
    public SignUpResponseDto(String username,String message,int code){
        this.username = username;
        this.message = message;
        this.code = code;
    }
}
