package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.request.AddCompanyRequestDto;
import com.teamfresh.voc.dto.response.AddCompanyResponseDto;
import com.teamfresh.voc.service.CompanyService;
import com.teamfresh.voc.service.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/add-company")
    public AddCompanyResponseDto addCompany(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody @Valid AddCompanyRequestDto req,
                                            BindingResult bindingResult){
        return companyService.addCompany(userDetails,req,bindingResult);
    }
}
