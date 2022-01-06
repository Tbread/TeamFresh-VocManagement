package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.Voc;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ViewVocListResponse {
    private int code;
    private String message;
    private List<Voc> vocList;

    @Builder
    public ViewVocListResponse(int code, String message, List<Voc> vocList) {
        this.code = code;
        this.message = message;
        this.vocList = vocList;
    }
}
