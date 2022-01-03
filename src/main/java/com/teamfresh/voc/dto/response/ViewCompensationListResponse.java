package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.CompensationSimple;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ViewCompensationListResponse {
    private boolean success;
    private String message;
    private List<CompensationSimple> compensationSimpleList;

    @Builder
    public ViewCompensationListResponse(boolean success,String message,List<CompensationSimple> compensationSimpleList){
        this.success = success;
        this.message = message;
        this.compensationSimpleList = compensationSimpleList;
    }
}
