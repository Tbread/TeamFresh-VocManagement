package com.teamfresh.voc.dto.request;

import com.teamfresh.voc.model.Voc;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class NewVocRequestDto {

    @NotBlank
    private String details;
    private Long compensationAmount;
    private Long companyId;
    private Long driverId;
}
