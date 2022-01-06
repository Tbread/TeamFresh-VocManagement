package com.teamfresh.voc.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdmitPenaltyResponseDto {

    private int code;
    private String message;
    private Long penaltyTotal;

    @Builder
    public AdmitPenaltyResponseDto(int code,String message,Long penaltyTotal){
        this.code = code;
        this.message = message;
        this.penaltyTotal = penaltyTotal;
    }
}
