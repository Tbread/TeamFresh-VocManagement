package com.teamfresh.voc.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ObjectionPenaltyResponseDto {

    private int code;
    private String message;

    @Builder
    public ObjectionPenaltyResponseDto(int code,String message){
        this.code = code;
        this.message = message;
    }
}
