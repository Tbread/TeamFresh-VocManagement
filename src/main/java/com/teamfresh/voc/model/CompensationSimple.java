package com.teamfresh.voc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CompensationSimple {
    private Long compensationId;
    private Long amount;
    private boolean checked;
    private boolean objection;

    @Builder
    public CompensationSimple(Compensation compensation){
        this.compensationId = compensation.getId();
        this.amount = compensation.getAmount();
        this.checked = compensation.isChecked();
        this.objection = compensation.isObjection();
    }
}
