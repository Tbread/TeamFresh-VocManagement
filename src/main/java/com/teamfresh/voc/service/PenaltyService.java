package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.response.AdmitPenaltyResponseDto;
import com.teamfresh.voc.dto.response.HandlingPenaltyResponseDto;
import com.teamfresh.voc.dto.response.ObjectionPenaltyResponseDto;
import com.teamfresh.voc.dto.response.ViewPenaltyListResponseDto;
import com.teamfresh.voc.model.*;
import com.teamfresh.voc.repository.CompensationRepository;
import com.teamfresh.voc.repository.PenaltyRepository;
import com.teamfresh.voc.repository.querydsl.CompanyQueryRepository;
import com.teamfresh.voc.repository.querydsl.DriverQueryRepository;
import com.teamfresh.voc.repository.querydsl.PenaltyQueryRepository;
import com.teamfresh.voc.service.userDetails.UserDetailsImpl;
import com.teamfresh.voc.util.MessageAssist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PenaltyService {

    private final PenaltyQueryRepository penaltyQueryRepository;
    private final PenaltyRepository penaltyRepository;
    private final CompensationRepository compensationRepository;
    private final MessageAssist ma;
    private final DriverQueryRepository driverQueryRepository;
    private final CompanyQueryRepository companyQueryRepository;

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
                // ????????? ???????????????
                sellerFault(compensationOptional.get());
            } else {
                // ????????? ???????????????
                Driver driver = driverQueryRepository.findById(compensationOptional.get().getVoc().getDriverId()).orElseThrow(() -> new UsernameNotFoundException("???????????? ?????? ?????? ID?????????."));
                penalty = driverFault(compensationOptional.get(), driver);
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
    public void sellerFault(Compensation compensation) {
        // ????????? ??????
        // Company ????????? ?????? ??????
        Company company = companyQueryRepository.findById(compensation.getVoc().getCompanyId()).orElseThrow(() -> new UsernameNotFoundException("???????????? ?????? ???????????????."));
        company.updatePenalty(compensation.getAmount());
        // ???????????? ??????
        compensation.getVoc().updateConclude();
    }

    public Penalty driverFault(Compensation compensation, Driver driver) {
        // ?????? ??????
        // ????????? ??????
        return Penalty.builder()
                .amount(compensation.getAmount())
                .compensation(compensation)
                .driver(driver)
                .build();
    }

    @Transactional
    public ViewPenaltyListResponseDto viewPenalty(UserDetailsImpl userDetails) {
        ViewPenaltyListResponseDto res;
        User user = userDetails.getUser();
        if (user.getDriver() == null) {
            res = ViewPenaltyListResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(ma.NotDriver)
                    .build();
        } else {
            List<Penalty> penaltyList = penaltyQueryRepository.findAllByDriverId(user.getDriver().getId());
            for (Penalty penalty : penaltyList) {
                penalty.getCompensation().getVoc().updateChecked();
            }
            res = ViewPenaltyListResponseDto.builder()
                    .code(HttpServletResponse.SC_OK)
                    .message(ma.Success)
                    .penaltyList(penaltyList)
                    .build();
        }
        return res;
    }

    @Transactional
    public ObjectionPenaltyResponseDto objectionPenalty(UserDetailsImpl userDetails, Long penaltyId) {
        ObjectionPenaltyResponseDto res;
        Optional<Penalty> penaltyOptional = penaltyQueryRepository.findById(penaltyId);
        if (!penaltyOptional.isPresent()) {
            res = ObjectionPenaltyResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(ma.WrongPenaltyId)
                    .build();
        } else {
            Driver thisDriver = userDetails.getUser().getDriver();
            Driver penaltyDriver = penaltyOptional.get().getDriver();
            if (!thisDriver.getId().equals(penaltyDriver.getId())) {
                res = ObjectionPenaltyResponseDto.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message(ma.NotPenaltyOwner)
                        .build();
            } else {
                if (penaltyOptional.get().isObjection()) {
                    res = ObjectionPenaltyResponseDto.builder()
                            .code(HttpServletResponse.SC_BAD_REQUEST)
                            .message(ma.AlreadyObjection)
                            .build();
                } else {
                    Voc voc = penaltyOptional.get().getCompensation().getVoc();
                    voc.updateObjection();
                    penaltyOptional.get().updateObjection();
                    res = ObjectionPenaltyResponseDto.builder()
                            .code(HttpServletResponse.SC_OK)
                            .message(ma.Success)
                            .build();
                }
            }
        }
        return res;
    }

    @Transactional
    public AdmitPenaltyResponseDto admitPenalty(UserDetailsImpl userDetails,Long penaltyId){
        AdmitPenaltyResponseDto res;
        Optional<Penalty> penaltyOptional = penaltyQueryRepository.findById(penaltyId);
        if(!penaltyOptional.isPresent()){
            res = AdmitPenaltyResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(ma.WrongPenaltyId)
                    .build();
        } else {
            Long thisId = userDetails.getUser().getDriver().getId();
            Long ownerId = penaltyOptional.get().getCompensation().getVoc().getDriverId();
            if (!thisId.equals(ownerId)){
                res = AdmitPenaltyResponseDto.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message(ma.NotPenaltyOwner)
                        .build();
            } else {
                //voc ??????
                penaltyOptional.get().getCompensation().getVoc().updateConclude();
                penaltyOptional.get().getDriver().updatePenalty(penaltyOptional.get());
                res = AdmitPenaltyResponseDto.builder()
                        .code(HttpServletResponse.SC_OK)
                        .message(ma.Success)
                        .penaltyTotal(userDetails.getUser().getDriver().getPenalty())
                        .build();
            }
        }
        return res;
    }
}
