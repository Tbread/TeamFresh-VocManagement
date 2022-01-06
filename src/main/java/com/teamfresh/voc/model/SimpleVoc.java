package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SimpleVoc {
    private Long vocId;
    private String details;
    private Voc.responsibility responsibility;
    private boolean checked;
    private boolean objection;
    private Long driverId;
    private Long companyId;
    private Long compensationId;
    private Long compensationAmount;

    @Builder
    public SimpleVoc(Long vocId,
                     String details,
                     Voc.responsibility responsibility,
                     boolean checked,
                     boolean objection,
                     Long driverId,
                     Long companyId,
                     Long compensationId,
                     Long compensationAmount){
        this.vocId =vocId;
        this.details = details;
        this.responsibility =responsibility;
        this.checked = checked;
        this.objection = objection;
        this.driverId = driverId;
        this.companyId = companyId;
        this.compensationAmount = compensationAmount;
        this.compensationId = compensationId;
    }
}
