package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.Compensation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ViewCompensationListResponseDto {

    private int code;
    private String message;
    private List<Compensation> compensationList;

    @Builder
    ViewCompensationListResponseDto(int code, String message, List<Compensation> compensationList) {
        this.code = code;
        this.compensationList = compensationList;
        this.message = message;
    }
}
