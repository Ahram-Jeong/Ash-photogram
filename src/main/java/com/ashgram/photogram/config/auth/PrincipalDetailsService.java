package com.ashgram.photogram.config.auth;

import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // return이 성공하면, 자동으로 UserDetails 타입의 세션 생성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // password는 security가 알아서 비교 검증 하므로, username만 검증하면 됨
        log.info("login_username = {}", username);
        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity);
        }
    }
}
