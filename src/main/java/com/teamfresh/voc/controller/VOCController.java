package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.request.WriteVOCRequestDto;
import com.teamfresh.voc.dto.response.WriteVOCResponseDto;
import com.teamfresh.voc.service.VOCService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VOCController {

    private final VOCService vocService;

    @GetMapping("/write-voc")
    public WriteVOCResponseDto writeVOC(@RequestBody @Valid WriteVOCRequestDto req, BindingResult bindingResult){
        return vocService.writeVOC(req,bindingResult);
    }
}
