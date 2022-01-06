package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.response.ViewCompensationListResponseDto;
import com.teamfresh.voc.repository.CompensationRepository;
import com.teamfresh.voc.util.MessageAssist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class CompensationService {

    private final CompensationRepository compensationRepository;
    private final MessageAssist ma;

    public ViewCompensationListResponseDto viewCompensationList() {
        return ViewCompensationListResponseDto.builder()
                .code(HttpServletResponse.SC_OK)
                .message(ma.Success)
                .compensationList(compensationRepository.findAll())
                .build();
    }
}
