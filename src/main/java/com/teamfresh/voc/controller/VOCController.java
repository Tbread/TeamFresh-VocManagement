package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.request.WriteVOCRequestDto;
import com.teamfresh.voc.dto.response.*;
import com.teamfresh.voc.service.VOCService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VOCController {

    private final VOCService vocService;

    @PostMapping("/write-voc")
    public WriteVOCResponseDto writeVoc(@RequestBody @Valid WriteVOCRequestDto req, BindingResult bindingResult){
        return vocService.write(req,bindingResult);
    }

    @GetMapping("/view-voc/{vocId}")
    public ViewVOCDetailsResponse viewVoc(@PathVariable Long vocId){
        return vocService.view(vocId);
    }

    @GetMapping("/view-voc")
    public ViewVOCListResponse viewVocList(){
        return vocService.viewList();
    }

    @GetMapping("view-comp/{compensationId}")
    public ViewCompensationDetailsResponse viewComp(@PathVariable Long compensationId){
        return vocService.viewComp(compensationId);
    }

    @GetMapping("/view-comp")
    public ViewCompensationListResponse viewCompList(){
        return vocService.viewCompList();
    }
}
