package com.teamfresh.voc.dto.request;

import com.teamfresh.voc.model.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
public class AddCompanyRequestDto {

    @NotBlank(message = "회사명은 필수 입력 값입니다.")
    private String companyName;
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String contactEmail;
    @Pattern(regexp = "[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}",message = "올바르지 않은 전화번호 형식입니다.")
    private String contactNum;
    @NotEmpty(message = "회사 타입은 필수 입력 값입니다.")
    private Company.type type;

}
