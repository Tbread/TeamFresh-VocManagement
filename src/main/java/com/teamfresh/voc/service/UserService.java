package com.teamfresh.voc.service;

import com.teamfresh.voc.dto.request.LoginRequestDto;
import com.teamfresh.voc.dto.request.SignUpRequestDto;
import com.teamfresh.voc.dto.response.LoginResponseDto;
import com.teamfresh.voc.dto.response.SignUpResponseDto;
import com.teamfresh.voc.jwt.JwtTokenProvider;
import com.teamfresh.voc.model.User;
import com.teamfresh.voc.repository.UserRepository;
import com.teamfresh.voc.util.MessageAssist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final MessageAssist ma;


    //가입
    @Transactional
    public SignUpResponseDto register(SignUpRequestDto req, BindingResult bindingResult) {
        SignUpResponseDto signUpResponseDto;
        if (bindingResult.hasErrors()) {
            signUpResponseDto = SignUpResponseDto.builder()
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                    .build();
        } else {
            String username = req.getUsername();
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                signUpResponseDto = SignUpResponseDto.builder()
                        .username(username)
                        .message(ma.DupUsername)
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
                        .message(ma.Success)
                        .code(HttpServletResponse.SC_OK)
                        .build();
            }
        }
        return signUpResponseDto;
    }

    //로그인
    public LoginResponseDto login(LoginRequestDto req, BindingResult bindingResult) {
        LoginResponseDto res;
        if (bindingResult.hasErrors()) {
            res = LoginResponseDto.builder()
                    .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
                    .code(HttpServletResponse.SC_BAD_REQUEST)
                    .build();
        } else {
            String reqUsername = req.getUsername();
            Optional<User> userOptional = userRepository.findByUsername(reqUsername);
            if (!userOptional.isPresent()) {
                res = LoginResponseDto.builder()
                        .code(HttpServletResponse.SC_BAD_REQUEST)
                        .message(ma.WrongUsernameOrPassword)
                        .build();
            } else {
                User user = userOptional.get();
                String rawPassword = req.getPassword();
                if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
                    res = LoginResponseDto.builder()
                            .code(HttpServletResponse.SC_BAD_REQUEST)
                            .message(ma.WrongUsernameOrPassword)
                            .build();
                } else {
                    Authentication usernamePassword = new UsernamePasswordAuthenticationToken(reqUsername, rawPassword);
                    Authentication authentication = authenticationManager.authenticate(usernamePassword);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String token = jwtTokenProvider.createToken(user.getId().toString(),user.getUsername());
                    res = LoginResponseDto.builder()
                            .code(HttpServletResponse.SC_OK)
                            .username(reqUsername)
                            .token(token)
                            .message(ma.Success)
                            .build();
                }
            }
        }
        return res;
    }

}