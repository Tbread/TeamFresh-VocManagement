package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.VOC;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WriteVOCResponseDto {

    private boolean success;
    private String message;
    private VOC voc;

    @Builder
    public WriteVOCResponseDto(boolean success, String message, VOC voc) {
        this.success = success;
        this.message = message;
        this.voc = voc;
    }
}
