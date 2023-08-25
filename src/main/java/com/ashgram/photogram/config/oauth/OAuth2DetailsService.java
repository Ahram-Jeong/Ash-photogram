package com.ashgram.photogram.config.oauth;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2DetailsService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> userInfo = (Map<String, Object>) oAuth2User.getAttributes().get("response");
        String username = "naver_" + (String) userInfo.get("id");
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String name = (String) userInfo.get("name");
        String email = (String) userInfo.get("email");

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) { // 네이버로 최초 로그인 시,
            User user = User.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .email(email)
                    .role("ROLE_USER")
                    .build();

            return new PrincipalDetails(userRepository.save(user), (Map<String, Object>) oAuth2User.getAttributes().get("response")); // 회원 가입
        } else {
            return new PrincipalDetails(userEntity, (Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }
    }
}
