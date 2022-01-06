package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.request.NewVocRequestDto;
import com.teamfresh.voc.dto.response.NewVocResponseDto;
import com.teamfresh.voc.dto.response.ViewVocListResponseDto;
import com.teamfresh.voc.service.VocService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voc")
public class VocController {
    private final VocService vocService;

    @PostMapping("/new-voc")
    public NewVocResponseDto newVoc(@RequestBody @Valid NewVocRequestDto req, BindingResult bindingResult){
        return vocService.newVoc(req,bindingResult);
    }

    @GetMapping("/view")
    public ViewVocListResponseDto viewVocList(){
        return vocService.viewVocList();
    }
}
