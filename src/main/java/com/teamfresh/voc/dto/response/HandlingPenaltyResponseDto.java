package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.Penalty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HandlingPenaltyResponseDto {

    private int code;
    private String message;
    private Penalty penalty;


    @Builder
    public HandlingPenaltyResponseDto(int code,String message,Penalty penalty){
        this.code = code;
        this.message = message;
        this.penalty = penalty;
    }
}
