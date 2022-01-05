package com.teamfresh.voc.dto.response;

import com.teamfresh.voc.model.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCompanyResponseDto {

    private int code;
    private String message;
    private Company company;

    @Builder
    public AddCompanyResponseDto(int code,String message,Company company){
        this.code = code;
        this.message = message;
        this.company = company;
    }
}
