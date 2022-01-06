package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.request.NewVocRequestDto;
import com.teamfresh.voc.dto.response.NewVocResponseDto;
import com.teamfresh.voc.dto.response.ViewVocListResponseDto;
import com.teamfresh.voc.model.Company;
import com.teamfresh.voc.model.Compensation;
import com.teamfresh.voc.model.SimpleVoc;
import com.teamfresh.voc.model.Voc;
import com.teamfresh.voc.repository.CompanyRepository;
import com.teamfresh.voc.repository.CompensationRepository;
import com.teamfresh.voc.repository.VocRepository;
import com.teamfresh.voc.util.MessageAssist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VocService {
    private final MessageAssist ma;
    private final VocRepository vocRepository;
    private final CompanyRepository companyRepository;
    private final CompensationRepository compensationRepository;

    @Transactional
    public NewVocResponseDto newVoc(NewVocRequestDto req, BindingResult bindingResult) {
        NewVocResponseDto res;
        Compensation compensation = null;
        Voc voc;
        if (bindingResult.hasErrors()) {
            res = NewVocResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                    .build();
        } else {
            Long companyId = req.getCompanyId();
            Optional<Company> companyOptional = companyRepository.findById(companyId);
            if (!companyOptional.isPresent()) {
                res = NewVocResponseDto.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message(ma.WrongCompanyId)
                        .build();
            } else {
                if (!req.getCompensationAmount().equals(0L)) {
                    // 배상 요청이 존재
                    compensation = Compensation.builder()
                            .amount(req.getCompensationAmount())
                            .build();
                    compensationRepository.save(compensation);
                }
                if (companyOptional.get().getType() == Company.type.DELIVERY) {
                    // 운송 회사 귀책
                    voc = Voc.builder()
                            .responsibility(Voc.responsibility.DRIVER)
                            .details(req.getDetails())
                            .compensation(compensation)
                            .driverId(req.getDriverId())
                            .companyId(req.getCompanyId())
                            .build();
                    vocRepository.save(voc);
                } else {
                    // 고객사 귀책
                    voc = Voc.builder()
                            .responsibility(Voc.responsibility.SELLER)
                            .details(req.getDetails())
                            .compensation(compensation)
                            .companyId(req.getCompanyId())
                            .build();
                    vocRepository.save(voc);
                }
                res = NewVocResponseDto.builder()
                        .voc(voc)
                        .code(HttpServletResponse.SC_OK)
                        .message(ma.Success)
                        .build();
            }
        }
        return res;
    }

    public ViewVocListResponseDto viewVocList() {
        ViewVocListResponseDto res;
        List<SimpleVoc> simpleVocList = new ArrayList<>();
        List<Voc> vocList = vocRepository.findAll();
        for (Voc voc : vocList) {
            if (voc.getCompensation() == null) {
                // 배상요구가 없을경우
                SimpleVoc simpleVoc = SimpleVoc.builder()
                        .vocId(voc.getId())
                        .checked(voc.isChecked())
                        .companyId(voc.getCompanyId())
                        .driverId(voc.getDriverId())
                        .objection(voc.isObjection())
                        .details(voc.getDetails())
                        .responsibility(voc.getResponsibility())
                        .build();
                simpleVocList.add(simpleVoc);
            } else {
                //배상요구가 있을경우
                SimpleVoc simpleVoc = SimpleVoc.builder()
                        .vocId(voc.getId())
                        .checked(voc.isChecked())
                        .companyId(voc.getCompanyId())
                        .driverId(voc.getDriverId())
                        .compensationAmount(voc.getCompensation().getAmount())
                        .compensationId(voc.getCompensation().getId())
                        .objection(voc.isObjection())
                        .details(voc.getDetails())
                        .responsibility(voc.getResponsibility())
                        .build();
                simpleVocList.add(simpleVoc);
            }
        }
        res = ViewVocListResponseDto.builder()
                .vocList(simpleVocList)
                .code(HttpServletResponse.SC_OK)
                .message(ma.Success)
                .build();
        return res;
    }

}
