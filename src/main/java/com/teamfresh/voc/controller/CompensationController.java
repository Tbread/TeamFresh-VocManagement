package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.response.ViewCompensationListResponseDto;
import com.teamfresh.voc.service.CompensationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/compensation")
public class CompensationController {

    private final CompensationService compensationService;

    @GetMapping("/view")
    public ViewCompensationListResponseDto viewCompensationList(){
        return compensationService.viewCompensationList();
    }
}
