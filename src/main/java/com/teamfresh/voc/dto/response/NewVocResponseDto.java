package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.Voc;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewVocResponseDto {
    private int code;
    private String message;
    private Voc voc;

    @Builder
    public NewVocResponseDto(int code,String message,Voc voc){
        this.code = code;
        this.message = message;
        this.voc = voc;
    }
}
