package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.request.NewVocRequestDto;
import com.teamfresh.voc.dto.response.NewVocResponseDto;
import com.teamfresh.voc.service.VocService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voc")
public class VocController {
    private final VocService vocService;

    @PostMapping("new-voc")
    public NewVocResponseDto newVoc(@RequestBody @Valid NewVocRequestDto req, BindingResult bindingResult){
        return vocService.newVoc(req,bindingResult);
    }
}
