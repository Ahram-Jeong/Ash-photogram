package com.ashgram.photogram.web.dto.auth;

import com.ashgram.photogram.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    @Size(min = 3, max = 20, message = "3 ~ 20자 이내로 입력하세요.")
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}
