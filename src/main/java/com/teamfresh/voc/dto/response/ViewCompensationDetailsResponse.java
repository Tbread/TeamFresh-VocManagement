package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.Compensation;
import com.teamfresh.voc.model.VOC;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ViewCompensationDetailsResponse {
    private boolean success;
    private String message;
    private Compensation compensation;
    private Long vocId;
    private String faultDetails;

    @Builder
    public ViewCompensationDetailsResponse(boolean success, String message, VOC voc){
        this.success = success;
        this.message = message;
        this.compensation = voc.getCompensation();
        this.vocId = voc.getId();
        this.faultDetails = getFaultDetails();
    }
}
