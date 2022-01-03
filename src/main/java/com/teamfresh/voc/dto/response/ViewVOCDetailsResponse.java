package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.VOC;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ViewVOCDetailsResponse {
    private boolean success;
    private String message;
    private VOC voc;

    @Builder
    public ViewVOCDetailsResponse(boolean success,String message,VOC voc){
        this.success = success;
        this.message = message;
        this.voc = voc;
    }
}
