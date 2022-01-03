package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.request.WriteVOCRequestDto;
import com.teamfresh.voc.dto.response.ViewVOCDetailsResponse;
import com.teamfresh.voc.dto.response.ViewVOCListResponse;
import com.teamfresh.voc.dto.response.WriteVOCResponseDto;
import com.teamfresh.voc.model.Compensation;
import com.teamfresh.voc.model.VOC;
import com.teamfresh.voc.model.VOCSimple;
import com.teamfresh.voc.repository.CompensationRepository;
import com.teamfresh.voc.repository.VOCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VOCService {

    private final VOCRepository vocRepository;
    private final CompensationRepository compensationRepository;

    // 새로운 voc 등륵
    @Transactional
    public WriteVOCResponseDto write(WriteVOCRequestDto req, BindingResult bindingResult) {
        WriteVOCResponseDto res;
        if (bindingResult.hasErrors()) {
            // validator 에서 에러 발생시
            res = WriteVOCResponseDto.builder()
                    .success(false)
                    .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                    .build();
        } else {
            // 운송사귀책
            if (req.getResponsibility().toString().equals("DRIVERFAULT")) {
                Compensation compensation = Compensation.builder()
                        .amount(req.getCompensationAmount())
                        .driverId(req.getDriverId())
                        .build();
                VOC voc = VOC.builder()
                        .faultDetails(req.getFaultDetails())
                        .responsibility(req.getResponsibility())
                        .compensation(compensation)
                        .build();
                vocRepository.save(voc);
                compensationRepository.save(compensation);
                res = WriteVOCResponseDto.builder()
                        .success(true)
                        .message("성공적으로 VOC가 추가되었습니다.")
                        .voc(voc)
                        .build();
            } else {
                //고객사귀책
                VOC voc = VOC.builder()
                        .faultDetails(req.getFaultDetails())
                        .responsibility(req.getResponsibility())
                        .build();
                vocRepository.save(voc);
                res = WriteVOCResponseDto.builder()
                        .voc(voc)
                        .success(true)
                        .message("성공적으로 VOC가 추가되었습니다.")
                        .build();
            }
        }
        return res;
    }

    // voc 상세내역 불러오기
    public ViewVOCDetailsResponse view(Long id) {
        ViewVOCDetailsResponse res;
        Optional<VOC> vocOptional = vocRepository.findById(id);
        if (!vocOptional.isPresent()) {
            //잘못된 id 입력시
            res = ViewVOCDetailsResponse.builder()
                    .success(false)
                    .message("존재하지 않는 VOC 입니다.")
                    .build();
        } else {
            VOC voc = vocOptional.get();
            res = ViewVOCDetailsResponse.builder()
                    .success(true)
                    .message("성공적으로 불러왔습니다")
                    .voc(voc)
                    .build();
        }
        return res;
    }

    // voc 리스트 불러오기
    public ViewVOCListResponse viewList() {
        ViewVOCListResponse res;
        List<VOC> vocList = vocRepository.findAllByOrderByIdAsc();
        List<VOCSimple> vocSimpleList = new ArrayList<>();
        for (VOC voc : vocList) {
            VOCSimple vocSimple = VOCSimple.builder()
                    .vocId(voc.getId())
                    .conclude(voc.isConclude())
                    .responsibility(voc.getResponsibility())
                    .build();
            vocSimpleList.add(vocSimple);
        }
        res = ViewVOCListResponse.builder()
                .success(true)
                .message("성공적으로 불러왔습니다.")
                .vocSimpleList(vocSimpleList)
                .build();
        return res;
    }


}
