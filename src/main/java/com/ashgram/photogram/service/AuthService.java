package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    // ***** 회원가입 *****
    public User join(User user) {
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
