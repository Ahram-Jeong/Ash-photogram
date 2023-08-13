package com.ashgram.photogram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 해당 파일로 시큐리티 활성화
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean // PW 암호화 Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // CSRF 토큰 해제
        http.authorizeRequests().antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated() // antMatchers(...)는 인증이 필요함
                .anyRequest().permitAll() // 그 외의 모든 요청은 허용
                .and().formLogin().loginPage("/auth/signin") // 인증 할 페이지
                .defaultSuccessUrl("/");
    }
}
