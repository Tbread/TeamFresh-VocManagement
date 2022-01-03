package com.teamfresh.voc.controller;

import com.teamfresh.voc.dto.request.WriteVOCRequestDto;
import com.teamfresh.voc.dto.response.ViewVOCDetailsResponse;
import com.teamfresh.voc.dto.response.ViewVOCListResponse;
import com.teamfresh.voc.dto.response.WriteVOCResponseDto;
import com.teamfresh.voc.service.VOCService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VOCController {

    private final VOCService vocService;

    @PostMapping("/write")
    public WriteVOCResponseDto write(@RequestBody @Valid WriteVOCRequestDto req, BindingResult bindingResult){
        return vocService.write(req,bindingResult);
    }

    @GetMapping("/view/{vocId}")
    public ViewVOCDetailsResponse view(@PathVariable Long vocId){
        return vocService.view(vocId);
    }

    @GetMapping("/view")
    public ViewVOCListResponse viewList(){
        return vocService.viewList();
    }
}
