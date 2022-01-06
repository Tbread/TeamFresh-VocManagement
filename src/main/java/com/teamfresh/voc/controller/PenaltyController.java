package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.response.HandlingPenaltyResponseDto;
import com.teamfresh.voc.service.userDetails.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/penalty")
public class PenaltyController {

    private final PenaltyService penaltyService;

    @GetMapping("/handling/{compensationId}")
    public HandlingPenaltyResponseDto handlingPenalty(@PathVariable Long compensationId){
        return penaltyService.handlingPenalty(compensationId);
    }
}
