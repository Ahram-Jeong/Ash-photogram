package com.ashgram.photogram.config;

import com.ashgram.photogram.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC
@EnableWebSecurity // 해당 파일로 시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean // PW 암호화 Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 토큰 해제
        http.authorizeRequests().antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated() // antMatchers(...)는 인증이 필요함
                .anyRequest().permitAll() // 그 외의 모든 요청은 허용
                .and().formLogin().loginPage("/auth/signin") // GET, 인증 할 페이지
                .loginProcessingUrl("/auth/signin") // POST, login 요청이 들어오면 스프링 시큐리티가 login 프로세스를 진행
                .defaultSuccessUrl("/")
                .and().oauth2Login() // formLogin + oauth2Login도 추가
                .userInfoEndpoint() // oauth2Login 이후 최종 응답을 회원 정보로 받음
                .userService(oAuth2DetailsService);
    }
}
