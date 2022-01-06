package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.response.AdmitPenaltyResponseDto;
import com.teamfresh.voc.dto.response.HandlingPenaltyResponseDto;
import com.teamfresh.voc.dto.response.ObjectionPenaltyResponseDto;
import com.teamfresh.voc.dto.response.ViewPenaltyListResponseDto;
import com.teamfresh.voc.service.PenaltyService;
import com.teamfresh.voc.service.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/penalty")
public class PenaltyController {

    private final PenaltyService penaltyService;

    @PostMapping("/handling/{compensationId}")
    public HandlingPenaltyResponseDto handlingPenalty(@PathVariable Long compensationId){
        return penaltyService.handlingPenalty(compensationId);
    }

    @GetMapping("/view")
    public ViewPenaltyListResponseDto viewPenalty(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return penaltyService.viewPenalty(userDetails);
    }

    @PostMapping("/objection/{penaltyId}")
    public ObjectionPenaltyResponseDto objectionPenalty(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long penaltyId){
        return penaltyService.objectionPenalty(userDetails,penaltyId);
    }

    @PostMapping("/admit/{penaltyId}")
    public AdmitPenaltyResponseDto admitPenalty(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long penaltyId){
        return penaltyService.admitPenalty(userDetails,penaltyId);
    }
}
