package com.teamfresh.voc.service.userDetails;


import com.teamfresh.voc.model.User;
import com.teamfresh.voc.repository.UserRepository;
import com.teamfresh.voc.repository.querydsl.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserQueryRepository userQueryRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userQueryRepository.findByUsername(username);
        return new UserDetailsImpl(user.orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 아이디입니다.")
        ));
    }
}
