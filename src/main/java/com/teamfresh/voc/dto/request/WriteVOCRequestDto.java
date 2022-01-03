package com.teamfresh.voc.dto.request;

import com.teamfresh.voc.model.VOC;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class WriteVOCRequestDto {

    @NotNull(message = "귀책사는 필수 입력값입니다.")
    private VOC.responsibility responsibility;

    private Long compensationAmount;

    @NotEmpty(message = "귀책 사유는 필수 입력값입니다.")
    private String faultDetails;

    private Long driverId;
}
