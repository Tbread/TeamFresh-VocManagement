package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.request.SignUpRequestDto;
import com.teamfresh.voc.dto.response.SignUpResponseDto;
import com.teamfresh.voc.jwt.JwtTokenProvider;
import com.teamfresh.voc.model.User;
import com.teamfresh.voc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponseDto register(SignUpRequestDto req, BindingResult bindingResult){
        SignUpResponseDto signUpResponseDto;
        if(bindingResult.hasErrors()){
            signUpResponseDto = SignUpResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                    .build();
        } else {
            String username = req.getUsername();
            Optional<User> userOptional = userRepository.findByUsername(username);
            if(userOptional.isPresent()){
                signUpResponseDto = SignUpResponseDto.builder()
                        .username(username)
                        .message("이미 존재하는 아이디입니다.")
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .build();
            } else {
                User user = User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(req.getRawPassword()))
                        .build();
                userRepository.save(user);
                signUpResponseDto = SignUpResponseDto.builder()
                        .username(username)
                        .message("성공적으로 가입되었습니다.")
                        .code(HttpServletResponse.SC_OK)
                        .build();
            }
        }
        return signUpResponseDto;
    }

}
