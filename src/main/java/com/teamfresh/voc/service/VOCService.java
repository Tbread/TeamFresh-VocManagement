package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.request.WriteVOCRequestDto;
import com.teamfresh.voc.dto.response.*;
import com.teamfresh.voc.model.Compensation;
import com.teamfresh.voc.model.CompensationSimple;
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

    // Compensation 은 VOC 에 종속되어있는 상태이므로 VOC 에서 처리함
    // 배상 정보 상세 보기
    public ViewCompensationDetailsResponse viewComp(Long id){
        ViewCompensationDetailsResponse res;
        Optional<Compensation> compensationOptional = compensationRepository.findById(id);
        if(!compensationOptional.isPresent()){
            res = ViewCompensationDetailsResponse.builder()
                    .success(false)
                    .message("존재하지 않는 배상정보입니다.")
                    .build();
        } else {
            Compensation compensation = compensationOptional.get();
            VOC voc = vocRepository.findByCompensationId(compensation.getId());
            res = ViewCompensationDetailsResponse.builder()
                    .message("성공적으로 불러왔습니다.")
                    .success(true)
                    .voc(voc)
                    .build();

        }
        return res;
    }

    //배상 정보 간략 리스트 보기
    public ViewCompensationListResponse viewCompList(){
        ViewCompensationListResponse res;
        List<Compensation> compensationList = compensationRepository.findAllByOrderByIdAsc();
        List<CompensationSimple> compensationSimpleList = new ArrayList<>();
        for (Compensation compensation : compensationList){
            CompensationSimple compensationSimple = CompensationSimple.builder()
                    .compensation(compensation)
                    .build();
            compensationSimpleList.add(compensationSimple);
        }
        res = ViewCompensationListResponse.builder()
                .compensationSimpleList(compensationSimpleList)
                .success(true)
                .message("성공적으로 불러왔습니다.")
                .build();
        return res;
    }
}
