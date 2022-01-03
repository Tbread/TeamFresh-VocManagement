package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.VOC;
import com.teamfresh.voc.model.VOCSimple;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ViewVOCListResponse {

    private boolean success;
    private String message;
    private List<VOCSimple> vocSimpleList;

    @Builder
    public ViewVOCListResponse(boolean success,String message,List<VOCSimple> vocSimpleList){
        this.success = success;
        this.message = message;
        this.vocSimpleList = vocSimpleList;
    }
}
