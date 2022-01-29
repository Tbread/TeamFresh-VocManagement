package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.request.AddCompanyRequestDto;
import com.teamfresh.voc.dto.response.AddCompanyResponseDto;
import com.teamfresh.voc.model.Company;
import com.teamfresh.voc.model.User;
import com.teamfresh.voc.repository.CompanyRepository;
import com.teamfresh.voc.repository.UserRepository;
import com.teamfresh.voc.repository.querydsl.CompanyQueryRepository;
import com.teamfresh.voc.repository.querydsl.UserQueryRepository;
import com.teamfresh.voc.service.userDetails.UserDetailsImpl;
import com.teamfresh.voc.util.MessageAssist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyQueryRepository companyQueryRepository;
    private final CompanyRepository companyRepository;
    private final MessageAssist msg;
    private final UserQueryRepository userQueryRepository;

    @Transactional
    public AddCompanyResponseDto addCompany(UserDetailsImpl userDetails, AddCompanyRequestDto req, BindingResult bindingResult) {
        AddCompanyResponseDto res;
        String username = userDetails.getUsername();
        User user = userQueryRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));
        if (user.getCompany() == null) {
            if (bindingResult.hasErrors()) {
                res = AddCompanyResponseDto.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                        .build();
            } else {
                if (!companyQueryRepository.findByCompanyName(req.getCompanyName()).isPresent()) {
                    Company company = Company.builder()
                            .companyName(req.getCompanyName())
                            .contactEmail(req.getContactEmail())
                            .contactNum(req.getContactNum())
                            .type(req.getType())
                            .build();
                    companyRepository.save(company);
                    user.updateCompany(company);
                    res = AddCompanyResponseDto.builder()
                            .code(HttpServletResponse.SC_OK)
                            .message(msg.Success)
                            .company(company)
                            .build();
                } else {
                    res = AddCompanyResponseDto.builder()
                            .code(HttpServletResponse.SC_BAD_REQUEST)
                            .message(msg.DupCompanyName)
                            .build();
                }
            }
        } else {
            res = AddCompanyResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(msg.AlreadyHasCompany)
                    .build();
        }
        return res;
    }
}
