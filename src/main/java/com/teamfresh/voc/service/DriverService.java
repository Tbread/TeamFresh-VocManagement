package com.teamfresh.voc.service;

import com.teamfresh.voc.model.Company;
import com.teamfresh.voc.model.Driver;
import com.teamfresh.voc.model.User;
import com.teamfresh.voc.repository.CompanyRepository;
import com.teamfresh.voc.repository.DriverRepository;
import com.teamfresh.voc.repository.UserRepository;
import com.teamfresh.voc.repository.querydsl.CompanyQueryRepository;
import com.teamfresh.voc.repository.querydsl.UserQueryRepository;
import com.teamfresh.voc.service.userDetails.UserDetailsImpl;
import com.teamfresh.voc.util.MessageAssist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.teamfresh.voc.dto.response.JoinDriverResponseDto;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final UserQueryRepository userQueryRepository;
    private final MessageAssist ma;
    private final CompanyQueryRepository companyQueryRepository;
    private final DriverRepository driverRepository;

    @Transactional
    public JoinDriverResponseDto joinDriver(UserDetailsImpl userDetails, Long companyId) {
        JoinDriverResponseDto res;
        User user = userQueryRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 유저입니다."));
        if (user.getDriver() != null) {
            res = JoinDriverResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(ma.AlreadyHasCompany)
                    .build();
        } else {
            Optional<Company> companyOptional = companyQueryRepository.findById(companyId);
            if(!companyOptional.isPresent()){
                res = JoinDriverResponseDto.builder()
                        .message(ma.WrongCompanyId)
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build();
            } else {
                user.updateCompany(companyOptional.get());
                Driver driver = Driver.builder()
                        .company(companyOptional.get())
                        .build();
                driverRepository.save(driver);
                user.updateDriver(driver);

                res = JoinDriverResponseDto.builder()
                        .code(HttpServletResponse.SC_OK)
                        .message(ma.Success)
                        .companyName(companyOptional.get().getCompanyName())
                        .build();
            }
        }
        return res;
    }
}
