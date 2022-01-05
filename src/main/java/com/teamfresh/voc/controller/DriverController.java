package com.teamfresh.voc.controller;


import com.teamfresh.voc.dto.response.JoinDriverResponseDto;
import com.teamfresh.voc.service.DriverService;
import com.teamfresh.voc.service.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/driver")
@RequiredArgsConstructor
@RestController
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/join-driver/{companyId}")
    public JoinDriverResponseDto joinDriver(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long companyId){
        return driverService.joinDriver(userDetails,companyId);
    }
}
