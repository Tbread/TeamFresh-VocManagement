package com.teamfresh.voc.controller;


import com.teamfresh.voc.dto.response.JoinDriverResponseDto;
import com.teamfresh.voc.service.DriverService;
import com.teamfresh.voc.service.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/driver")
@RequiredArgsConstructor
@RestController
public class DriverController {
    private final DriverService driverService;

    @GetMapping("/joinDrvier/{companyId}")
    public JoinDriverResponseDto joinDriver(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long companyId){
        return driverService.joinDriver(userDetails,companyId);
    }
}
