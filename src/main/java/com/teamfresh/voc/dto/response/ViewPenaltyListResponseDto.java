package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.Penalty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ViewPenaltyListResponseDto {

    private int code;
    private String message;
    private List<Penalty> penaltyList;

    @Builder
    public ViewPenaltyListResponseDto(int code, String message, List<Penalty> penaltyList) {
        this.code = code;
        this.message = message;
        this.penaltyList = penaltyList;
    }
}
