package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.request.SignUpRequestDto;
import com.teamfresh.voc.dto.response.SignUpResponseDto;
import com.teamfresh.voc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public SignUpResponseDto register(@RequestBody @Valid SignUpRequestDto req, BindingResult bindingResult){
        return userService.register(req,bindingResult);
    }
}
