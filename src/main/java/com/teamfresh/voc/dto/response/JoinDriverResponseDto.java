package com.teamfresh.voc.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinDriverResponseDto {
    private int code;
    private String message;
    private String companyName;

    @Builder
    public JoinDriverResponseDto(int code, String message, String companyName) {
        this.companyName = companyName;
        this.code = code;
        this.message = message;
    }
}
