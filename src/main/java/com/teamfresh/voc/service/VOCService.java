package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.request.WriteVOCRequestDto;
import com.teamfresh.voc.dto.response.WriteVOCResponseDto;
import com.teamfresh.voc.model.VOC;
import com.teamfresh.voc.repository.VOCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class VOCService {

    private final VOCRepository vocRepository;

    // 새로운 voc 등륵
    public WriteVOCResponseDto writeVOC(WriteVOCRequestDto req, BindingResult bindingResult){
        WriteVOCResponseDto res;
        if(bindingResult.hasErrors()){
            // validator 에서 에러 발생시
            res = WriteVOCResponseDto.builder()
                    .success(false)
                    .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                    .build();
        } else {
            VOC voc = VOC.builder()
                    .compensationAmount(req.getCompensationAmount())
                    .faultDetails(req.getFaultDetails())
                    .responsibility(req.getResponsibility())
                    .driverId(req.getDriverId())
                    .build();
            vocRepository.save(voc);
            res = WriteVOCResponseDto.builder()
                    .success(true)
                    .message("성공적으로 VOC가 추가되었습니다.")
                    .voc(voc)
                    .build();
        }
        return res;


    }
}
