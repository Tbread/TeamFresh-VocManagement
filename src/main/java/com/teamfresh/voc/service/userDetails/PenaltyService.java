package com.teamfresh.voc.service.userDetails;

import com.teamfresh.voc.dto.response.HandlingPenaltyResponseDto;
import com.teamfresh.voc.model.*;
import com.teamfresh.voc.repository.CompanyRepository;
import com.teamfresh.voc.repository.CompensationRepository;
import com.teamfresh.voc.repository.DriverRepository;
import com.teamfresh.voc.repository.PenaltyRepository;
import com.teamfresh.voc.util.MessageAssist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final CompensationRepository compensationRepository;
    private final MessageAssist ma;
    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public HandlingPenaltyResponseDto handlingPenalty(Long compensationId) {
        HandlingPenaltyResponseDto res;
        Penalty penalty = null;
        Optional<Compensation> compensationOptional = compensationRepository.findById(compensationId);
        if (!compensationOptional.isPresent()) {
            res = HandlingPenaltyResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(ma.WrongCompensationId)
                    .build();
        } else {
            if (compensationOptional.get().getVoc().getResponsibility().toString().equals("SELLER")) {
                // 고객사 귀책일경우
                sellerFault(compensationOptional.get());
            } else {
                // 운송사 귀책일경우
                Driver driver = driverRepository.findById(compensationOptional.get().getVoc().getDriverId()).orElseThrow(()->new UsernameNotFoundException("존재하지 않는 기사 ID입니다."));
                penalty = driverFault(compensationOptional.get(),driver);
                penaltyRepository.save(penalty);
            }
            res = HandlingPenaltyResponseDto.builder()
                    .penalty(penalty)
                    .code(HttpServletResponse.SC_OK)
                    .message(ma.Success)
                    .build();
        }
        return res;
    }

    @Transactional
    public void sellerFault(Compensation compensation){
        // 고객사 귀책
        // Company 객체에 즉시 적용
        Company company = companyRepository.findById(compensation.getVoc().getCompanyId()).orElseThrow(()->new UsernameNotFoundException("존재하지 않는 회사입니다."));
        company.updatePenalty(compensation.getAmount());
    }
    public Penalty driverFault(Compensation compensation,Driver driver){
        // 기사 귀책
        // 패널티 작성
        return Penalty.builder()
                .amount(compensation.getAmount())
                .compensation(compensation)
                .driver(driver)
                .build();
    }
}
